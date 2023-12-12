package com.example.common.domain;

public class OptimisticLockUpdateFailureException extends AbstractDomainException {

    public OptimisticLockUpdateFailureException(final Throwable cause) {
        super(CommonErrorCode.OPTIMISTIC_LOCK_UPDATE_FAILURE, cause);
    }
}
