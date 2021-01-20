package net.deanly.demo.domain.integrated_searcher.core.values;

/**
 * Priority Types.
 * 정책의 종류.
 *
 * Conflict Level: ALWAYS > PREFER > AVOID
 */
public enum PriorityQueryType {
    /**
     * Always selected, exception throwing when no available Query Service.
     *
     * 항상 선택하며, 지원 가능한 QueryService 가 없을 경우 예외처리.
     * 복수 정의된 경우 Translator 로직에 의해서 결정됨.
     */
    ALWAYS,

    /**
     * Preferred select, if no available Query Service, select an alternative.
     *
     * 우선 선택하며, 지원 가능한 Query Service 가 없을 경우 대안 선택.
     * 복수 정의된 경우 Translator 로직에 의해서 결정됨.
     */
    PREFER,

    /**
     * Avoid selected.
     *
     * 항상 선택하지 않는다.
     */
    AVOID
}
