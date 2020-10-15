package br.com.brainweb.interview.core.mapper;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

public class ListDozerBeanMapper extends DozerBeanMapper {

    public <T> List<T> mapAsList(Iterable<?> sources, Class<T> destinationClass) {
        ArrayList<T> targets = new ArrayList<T>();
        for (Object source : sources) {
            targets.add(map(source, destinationClass));
        }
        return targets;
    }
	
}
