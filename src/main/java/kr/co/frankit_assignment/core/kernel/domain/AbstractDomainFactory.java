package kr.co.frankit_assignment.core.kernel.domain;

import org.springframework.data.domain.AbstractAggregateRoot;

public interface AbstractDomainFactory<T extends AbstractAggregateRoot<T>> {
    T create();
}
