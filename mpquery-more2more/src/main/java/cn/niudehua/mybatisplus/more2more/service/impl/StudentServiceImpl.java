package cn.niudehua.mybatisplus.more2more.service.impl;

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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    private final StuSubRelationMapper stuSubRelationMapper;
    private final SubjectMapper subjectMapper;
    private final ISubjectService subjectService;

    public StudentServiceImpl(StuSubRelationMapper stuSubRelationMapper, SubjectMapper subjectMapper, ISubjectService subjectService) {
        this.stuSubRelationMapper = stuSubRelationMapper;
        this.subjectMapper = subjectMapper;
        this.subjectService = subjectService;
    }

    @Override
    public StudentVo getStudent(Integer stuId) {
        // 通过主键查询学生信息
        StudentVo studentVo = EntityConvertUtils.toObj(getById(stuId), StudentVo::new);
        LambdaQueryWrapper<StuSubRelation> wrapper = Wrappers.lambdaQuery(StuSubRelation.class).eq(StuSubRelation::getStuId, stuId);
        // 查询匹配关系
        List<StuSubRelation> stuSubRelations = stuSubRelationMapper.selectList(wrapper);
        Set<Integer> subIds = stuSubRelations.stream().map(StuSubRelation::getSubId).collect(toSet());
        if (studentVo != null && !subIds.isEmpty()) {
            LambdaQueryWrapper<Subject> queryWrapper = Wrappers.lambdaQuery(Subject.class).in(Subject::getSubId, subIds);
            List<SubjectBo> subBoList = EntityConvertUtils.toList(subjectService.list(queryWrapper), SubjectBo::new);
            Table<Integer, Integer, Integer> table = getHashBasedTable(stuSubRelations);
            subBoList.forEach(e -> e.setScore(table.get(stuId, e.getSubId())));
            studentVo.setSubList(subBoList);
        }
        return studentVo;
    }

    @Override
    public List<StudentVo> getStudentList() {
        // 通过主键查询学生信息
        List<StudentVo> studentVoList = EntityConvertUtils.toList(list(), StudentVo::new);
        // 批量查询学生ID
        Set<Integer> stuIds = EntityConvertUtils.toSet(studentVoList, Student::getStuId);
        LambdaQueryWrapper<StuSubRelation> wrapper = Wrappers.lambdaQuery(StuSubRelation.class).in(StuSubRelation::getStuId, stuIds);
        List<StuSubRelation> stuSubRelations = stuSubRelationMapper.selectList(wrapper);
        // 批量查询课程ID
        Set<Integer> subIds = EntityConvertUtils.toSet(stuSubRelations, StuSubRelation::getSubId);
        // 非常重要
        Map<Integer, List<Integer>> map = stuSubRelations.stream().collect(groupingBy(StuSubRelation::getStuId, mapping(StuSubRelation::getSubId, toList())));
        if (!stuIds.isEmpty() && !subIds.isEmpty()) {
            // Guava 双键Map
            Table<Integer, Integer, Integer> table = getHashBasedTable(stuSubRelations);
            LambdaQueryWrapper<Subject> queryWrapper = Wrappers.lambdaQuery(Subject.class).in(Subject::getSubId, subIds);
            List<SubjectBo> subjectBoList = EntityConvertUtils.toList(subjectService.list(queryWrapper), SubjectBo::new);
            for (StudentVo studentVo : studentVoList) {
                // 获取课程列表
                fillScore(table, map, subjectBoList, studentVo);
            }
        }
        return studentVoList;
    }

    @Override
    public IPage<StudentVo> getStudentPage(IPage<Student> page) {
        // 通过主键查询学生信息
        IPage<StudentVo> studentVoPage = EntityConvertUtils.toPage(page(page), StudentVo::new);
        // 批量查询学生ID
        Set<Integer> stuIds = studentVoPage.getRecords().stream().map(Student::getStuId).collect(toSet());
        LambdaQueryWrapper<StuSubRelation> wrapper = Wrappers.lambdaQuery(StuSubRelation.class).in(StuSubRelation::getStuId, stuIds);
        // 通过学生ID查询课程分数
        List<StuSubRelation> stuSubRelations = stuSubRelationMapper.selectList(wrapper);
        // 批量查询课程ID
        Set<Integer> subIds = stuSubRelations.stream().map(StuSubRelation::getSubId).collect(toSet());
        if (!stuIds.isEmpty() && !subIds.isEmpty()) {
            Table<Integer, Integer, Integer> table = getHashBasedTable(stuSubRelations);
            // 学生ID查询课程ID组
            Map<Integer, List<Integer>> map = stuSubRelations.stream().collect(groupingBy(StuSubRelation::getStuId, mapping(StuSubRelation::getSubId, toList())));

            List<Subject> subList = subjectMapper.selectList(Wrappers.lambdaQuery(Subject.class).in(Subject::getSubId, subIds));
            List<SubjectBo> subBoList = EntityConvertUtils.toList(subList, SubjectBo::new);
            for (StudentVo studentVo : studentVoPage.getRecords()) {
                fillScore(table, map, subBoList, studentVo);
            }

        }
        return studentVoPage;
    }

    private static void fillScore(Table<Integer, Integer, Integer> table, Map<Integer, List<Integer>> map, List<SubjectBo> subBoList, StudentVo studentVo) {
        List<SubjectBo> list = subBoList.stream().filter(e -> map.get(studentVo.getStuId()) != null && map.get(studentVo.getStuId()).contains(e.getSubId())).collect(Collectors.toList());
        // 填充分数
        list.forEach(e -> e.setScore(table.get(studentVo.getStuId(), e.getSubId())));
        studentVo.setSubList(list);
    }

    private Table<Integer, Integer, Integer> getHashBasedTable(List<StuSubRelation> stuSubRelations) {
        Table<Integer, Integer, Integer> table = HashBasedTable.create();
        stuSubRelations.forEach(e -> table.put(e.getStuId(), e.getSubId(), e.getScore()));
        return table;
    }
}
