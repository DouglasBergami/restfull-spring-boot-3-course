package br.com.coursespring.mappers;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class GenericMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destionationObjects = new ArrayList<>();

        for(O o: origin) {
            destionationObjects.add(mapper.map(o, destination));
        }
        return destionationObjects;
    }
}
