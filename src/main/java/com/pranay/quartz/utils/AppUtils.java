package com.pranay.quartz.utils;

import com.pranay.quartz.exception.BadRequestException;
import com.pranay.quartz.models.entity.MailSchedule;
import com.pranay.quartz.models.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class AppUtils {

    private static final Validator validator;

    private AppUtils() {
    }

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> void validateAndThrow(T obj) {
        Set<String> result = validate(obj);
        if (!CollectionUtils.isEmpty(result)) {
            String errorMessage = StringUtils.join(result, ",");
            throw new BadRequestException(errorMessage);
        }
    }

    private static <T> Set<String> validate(T obj) {
        Set<ConstraintViolation<T>> set = validator.validate(obj);
        if (!CollectionUtils.isEmpty(set)) {
            Set<String> messageSet = new HashSet<>();
            for (ConstraintViolation<T> constraintViolation : set) {
                messageSet.add(constraintViolation.getMessage());
            }
            return messageSet;
        }
        return Collections.emptySet();
    }


    public static List<Response> getResponseDtoListFrom(List<MailSchedule> mailSchedules) {
        ArrayList<Response> responseList = new ArrayList<>();
        if (mailSchedules != null) {
            for (MailSchedule mailSchedule : mailSchedules) {
                responseList.add(mailSchedule.toResponse());
            }
            Collections.sort(responseList, (o1, o2) -> o1.getScheduledTime().compareTo(o2.getScheduledTime()));
        }
        return responseList;
    }


}
