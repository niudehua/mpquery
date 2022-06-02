package cn.niudehua.mybatisplus.one2more.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class EntityConvertUtils {

    public EntityConvertUtils() {
    }

    public static <R, S, T, A> R collectList(Collection<S> source, Function<? super S, ? extends T> action,
            Collector<? super T, A, R> collector) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(collector);
        return source.stream().map(action).collect(collector);
    }

    public static <S, T> Set<T> collectSet(Collection<S> source, Function<? super S, ? extends T> action) {
        Objects.requireNonNull(source);
        return source.stream().map(action).collect(Collectors.toSet());
    }

    public static <S> List<? extends S> collectList(Collection<S> source, Function<? super S, ? extends S> action) {
        return collectList(source, action, Collectors.toList());
    }

    public static <T, R> R toObj(T source, Function<? super T, ? extends R> action) {
        Objects.requireNonNull(action);
        return Optional.ofNullable(source).map(action).orElse(null);
    }

    public static <T, R> List<R> toList(Collection<T> source, Function<? super T, ? extends R> action) {
        Objects.requireNonNull(action);
        return Objects.nonNull(source) ? source.stream().map(action).collect(Collectors.toList())
                : Lists.newArrayList();
    }

    public static <E extends IPage<T>, T, R> IPage<R> toPage(E source, Function<? super T, ? extends R> action) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(action);
        return source.convert(action);
    }

    public static <T, K, V> Map<K, V> toMap(Collection<T> lists, Function<? super T, ? extends K> keyAction,
            Function<? super T, ? extends V> valueAction) {
        Objects.requireNonNull(lists);
        Objects.requireNonNull(keyAction);
        Objects.requireNonNull(valueAction);
        return lists.stream().collect(Collectors.toMap(keyAction, valueAction));
    }

    public static <T, R> Set<R> toSet(Collection<T> source, Function<? super T, ? extends R> action) {
        Objects.requireNonNull(action);
        return Objects.nonNull(source) ? source.stream().map(action).collect(Collectors.toSet()) : Sets.newHashSet();
    }
}
