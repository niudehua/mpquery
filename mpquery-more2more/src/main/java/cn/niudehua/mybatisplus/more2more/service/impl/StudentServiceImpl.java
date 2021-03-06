package cn.niudehua.mybatisplus.more2more.service.impl;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import cn.niudehua.mybatisplus.more2more.domain.StuSubRelation;
import cn.niudehua.mybatisplus.more2more.domain.Student;
import cn.niudehua.mybatisplus.more2more.domain.Subject;
import cn.niudehua.mybatisplus.more2more.entity.bo.SubjectBo;
import cn.niudehua.mybatisplus.more2more.entity.vo.StudentVo;
import cn.niudehua.mybatisplus.more2more.mapper.StuSubRelationMapper;
import cn.niudehua.mybatisplus.more2more.mapper.StudentMapper;
import cn.niudehua.mybatisplus.more2more.mapper.SubjectMapper;
import cn.niudehua.mybatisplus.more2more.service.IStudentService;
import cn.niudehua.mybatisplus.more2more.service.ISubjectService;
import cn.niudehua.mybatisplus.more2more.util.EntityConvertUtils;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    private final StuSubRelationMapper stuSubRelationMapper;
    private final SubjectMapper subjectMapper;
    private final ISubjectService subjectService;

    public StudentServiceImpl(StuSubRelationMapper stuSubRelationMapper, SubjectMapper subjectMapper,
            ISubjectService subjectService) {
        this.stuSubRelationMapper = stuSubRelationMapper;
        this.subjectMapper = subjectMapper;
        this.subjectService = subjectService;
    }

    @Override
    public StudentVo getStudent(Integer stuId) {
        // ??????????????????????????????
        StudentVo studentVo = EntityConvertUtils.toObj(getById(stuId), StudentVo::new);
        LambdaQueryWrapper<StuSubRelation> wrapper = Wrappers.lambdaQuery(StuSubRelation.class)
            .eq(StuSubRelation::getStuId, stuId);
        // ??????????????????
        List<StuSubRelation> stuSubRelations = stuSubRelationMapper.selectList(wrapper);
        Set<Integer> subIds = stuSubRelations.stream().map(StuSubRelation::getSubId).collect(toSet());
        if (studentVo != null && subIds.size() > 0) {
            LambdaQueryWrapper<Subject> queryWrapper = Wrappers.lambdaQuery(Subject.class)
                .in(Subject::getSubId, subIds);
            List<SubjectBo> subBoList = EntityConvertUtils.toList(subjectService.list(queryWrapper), SubjectBo::new);
            Table<Integer, Integer, Integer> table = getHashBasedTable(stuSubRelations);
            subBoList.forEach(e -> e.setScore(table.get(stuId, e.getSubId())));
            studentVo.setSubList(subBoList);
        }
        return studentVo;
    }

    @Override
    public List<StudentVo> getStudentList() {
        // ??????????????????????????????
        List<StudentVo> studentVoList = EntityConvertUtils.toList(list(), StudentVo::new);
        // ??????????????????ID
        Set<Integer> stuIds = EntityConvertUtils.toSet(studentVoList, Student::getStuId);
        LambdaQueryWrapper<StuSubRelation> wrapper = Wrappers.lambdaQuery(StuSubRelation.class)
            .in(StuSubRelation::getStuId, stuIds);
        List<StuSubRelation> stuSubRelations = stuSubRelationMapper.selectList(wrapper);
        // ??????????????????ID
        Set<Integer> subIds = EntityConvertUtils.toSet(stuSubRelations, StuSubRelation::getSubId);
        // ????????????
        Map<Integer, List<Integer>> map = stuSubRelations.stream()
            .collect(groupingBy(StuSubRelation::getStuId, mapping(StuSubRelation::getSubId, toList())));
        if (stuIds.size() > 0 && subIds.size() > 0) {
            // Guava ??????Map
            Table<Integer, Integer, Integer> table = getHashBasedTable(stuSubRelations);
            LambdaQueryWrapper<Subject> queryWrapper = Wrappers.lambdaQuery(Subject.class)
                .in(Subject::getSubId, subIds);
            List<SubjectBo> subjectBoList = EntityConvertUtils.toList(subjectService.list(queryWrapper),
                    SubjectBo::new);
            for (StudentVo studentVo : studentVoList) {
                // ??????????????????
                List<SubjectBo> list = subjectBoList.stream()
                    .filter(e -> map.get(studentVo.getStuId()) != null
                            && map.get(studentVo.getStuId()).contains(e.getSubId()))
                    .collect(Collectors.toList());
                // ????????????
                list.forEach(e -> e.setScore(table.get(studentVo.getStuId(), e.getSubId())));
                studentVo.setSubList(list);
            }
        }
        return studentVoList;
    }

    @Override
    public IPage<StudentVo> getStudentPage(IPage<Student> page) {
        // ??????????????????????????????
        IPage<StudentVo> studentVoPage = EntityConvertUtils.toPage(page(page), StudentVo::new);
        // ??????????????????ID
        Set<Integer> stuIds = studentVoPage.getRecords().stream().map(Student::getStuId).collect(toSet());
        LambdaQueryWrapper<StuSubRelation> wrapper = Wrappers.lambdaQuery(StuSubRelation.class)
            .in(StuSubRelation::getStuId, stuIds);
        // ????????????ID??????????????????
        List<StuSubRelation> stuSubRelations = stuSubRelationMapper.selectList(wrapper);
        // ??????????????????ID
        Set<Integer> subIds = stuSubRelations.stream().map(StuSubRelation::getSubId).collect(toSet());
        if (stuIds.size() > 0 && subIds.size() > 0) {
            Table<Integer, Integer, Integer> table = getHashBasedTable(stuSubRelations);
            // ??????ID????????????ID???
            Map<Integer, List<Integer>> map = stuSubRelations.stream()
                .collect(groupingBy(StuSubRelation::getStuId, mapping(StuSubRelation::getSubId, toList())));

            List<Subject> subList = subjectMapper
                .selectList(Wrappers.lambdaQuery(Subject.class).in(Subject::getSubId, subIds));
            List<SubjectBo> subBoList = EntityConvertUtils.toList(subList, SubjectBo::new);
            for (StudentVo studentVo : studentVoPage.getRecords()) {
                // ??????????????????
                List<SubjectBo> list = subBoList.stream()
                    .filter(e -> map.get(studentVo.getStuId()) != null
                            && map.get(studentVo.getStuId()).contains(e.getSubId()))
                    .collect(Collectors.toList());
                // ????????????
                list.forEach(e -> e.setScore(table.get(studentVo.getStuId(), e.getSubId())));
                studentVo.setSubList(list);
            }

        }
        return studentVoPage;
    }

    private Table<Integer, Integer, Integer> getHashBasedTable(List<StuSubRelation> stuSubRelations) {
        Table<Integer, Integer, Integer> table = HashBasedTable.create();
        stuSubRelations.forEach(e -> table.put(e.getStuId(), e.getSubId(), e.getScore()));
        return table;
    }
}
