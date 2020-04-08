package condition;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.Preconditions;

import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;
import static org.junit.platform.commons.util.AnnotationUtils.findAnnotation;

public class DisabledTimeCondition implements ExecutionCondition {

    private static final ConditionEvaluationResult ENABLED_BY_DEFAULT = enabled("@DisabledOnTime is not present");
    private static final ConditionEvaluationResult ENABLED_ON_CURRENT_TIME = enabled("Enabled on time");
    private static final ConditionEvaluationResult DISABLED_ON_CURRENT_TIME = disabled("Disabled on time");

    private static final String DEFAULT_ZONE_ID = "JST";

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Optional<DisabledOnTime> optional = findAnnotation(context.getElement(), DisabledOnTime.class);

        if (optional.isEmpty()) {
            return ENABLED_BY_DEFAULT;
        }

        DisabledOnTime annotation = optional.get();
        String fromStr = annotation.from();
        Preconditions.notBlank(fromStr, () -> "The 'from' attribute must not be blank in " + annotation);
        String toStr = annotation.to();
        Preconditions.notBlank(toStr, () -> "The 'to' attribute must not be blank in " + annotation);

        LocalTime from = convertLocalTime(fromStr);
        Preconditions.condition(from != null, () -> "The 'from' attribute format must be 'HH:MM' in " + annotation);
        LocalTime to = convertLocalTime(toStr);
        Preconditions.condition(to != null, () -> "The 'to' attribute format must be 'HH:MM' in " + annotation);

        Preconditions.condition(from.isBefore(to), () -> "The 'from' must be before to in " + annotation);

        ZoneId zoneId = convertZoneId(annotation.zoneId());
        Preconditions.condition(zoneId != null, () -> "The 'zoneId' is not found in " + annotation);

        return isBetweenOrEquals(LocalTime.now(zoneId), from, to) ? DISABLED_ON_CURRENT_TIME : ENABLED_ON_CURRENT_TIME;
    }

    private LocalTime convertLocalTime(String timeStr) {
        try {
            return LocalTime.parse(timeStr);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private ZoneId convertZoneId(String zoneIdStr) {
        return ZoneId.of(zoneIdStr, ZoneId.SHORT_IDS);
    }

    private boolean isBetweenOrEquals(LocalTime target, LocalTime from, LocalTime to) {
        if (target.equals(from) || target.equals(to)) {
            return true;
        }
        if (from.isBefore(target) && to.isAfter(target)) {
            return true;
        }
        return false;
    }
}
