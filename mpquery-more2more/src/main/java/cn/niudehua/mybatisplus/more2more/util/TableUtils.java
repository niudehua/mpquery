package cn.niudehua.mybatisplus.more2more.util;

import java.util.Objects;
import java.util.function.Function;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TableUtils {

    public static <T, R, C, V> Table<R, C, V> createHashTable(Iterable<T> source, Function<? super T, ? extends R> r,
            Function<? super T, ? extends C> c, Function<? super T, ? extends V> v) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(r);
        Objects.requireNonNull(c);
        Objects.requireNonNull(v);
        Table<R, C, V> table = HashBasedTable.create();
        source.forEach((e) -> table.put(EntityConvertUtils.toObj(e, r), EntityConvertUtils.toObj(e, c),
                EntityConvertUtils.toObj(e, v)));
        return table;
    }

    public static <R, C, T> Table<R, C, T> createHashTable(Iterable<T> source, Function<? super T, ? extends R> r,
            Function<? super T, ? extends C> c) {
        return createHashTable(source, r, c, (e) -> e);
    }

}
