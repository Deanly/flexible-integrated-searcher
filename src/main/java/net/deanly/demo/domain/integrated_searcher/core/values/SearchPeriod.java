package net.deanly.demo.domain.integrated_searcher.core.values;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Data
@ToString
public final class SearchPeriod {
    //default time zone
    private ZoneId defaultZoneId = ZoneId.systemDefault();

    private LocalDateTime start;
    private LocalDateTime end;

    /**
     *
     * @param start {String} "yyyy-MM-dd HH:mm:ss" or "yyyy-MM-dd"
     * @param end {String} "yyyy-MM-dd HH:mm:ss" or "yyyy-MM-dd"
     */
    public SearchPeriod(String start, String end) {
        if (!Strings.isNullOrEmpty(start)) {
            try { this.start = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); }
            catch (DateTimeParseException ignore) { this.start = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(); }
        }
        if (!Strings.isNullOrEmpty(end)) {
            try { this.end = LocalDateTime.parse(end,  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); }
            catch (DateTimeParseException ignore) { this.end = LocalDate.parse(end,  DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(); }
        }
    }
    public SearchPeriod(int startYears, int startMonth, int startDays, int endYears, int endMonth, int endDays) {
        this.start = LocalDateTime.of(startYears, startMonth, startDays, 0, 0, 0);
        this.end = LocalDateTime.of(endYears, endMonth, endDays, 0, 0, 0);
    }
    public SearchPeriod(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
    public SearchPeriod(Date start, Date end) {
        if (!Objects.isNull(start)) this.start = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        if (!Objects.isNull(end)) this.end = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());
    }

    // `true` 일경우 닫힘 상태 <=, `false` 일 경우 열람 상태 <
    public boolean isClosedStart = true;
    public boolean isClosedEnd = true;

    public Long between() {
        if (!Objects.isNull(this.start) && !Objects.isNull(this.end)) {
            return ChronoUnit.DAYS.between(this.start, this.end);
        } else {
            return null;
        }
    }

    public Date getStartDate() {
        if (Objects.isNull(this.start)) return null;
        else {
            if (this.isClosedStart) {
                return Date.from(this.start.atZone(this.defaultZoneId).toInstant());
            } else {
                return Date.from(this.start.plusNanos(100000000).atZone(this.defaultZoneId).toInstant());
            }
        }
    }

    public Date getEndDate() {
        if (Objects.isNull(end)) return null;
        else {
            if (this.isClosedStart) {
                return Date.from(this.end.atZone(this.defaultZoneId).toInstant());
            } else {
                return Date.from(this.end.minusNanos(100000000).atZone(this.defaultZoneId).toInstant());
            }
        }
    }

    public void setStartDate(String startDate) {
        if (!Strings.isNullOrEmpty(startDate)) {
            try { this.start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); }
            catch (DateTimeParseException ignore) { this.start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(); }
        }
    }

    public void setEndDate(String endDate) {
        if (!Strings.isNullOrEmpty(endDate)) {
            try { this.end = LocalDateTime.parse(endDate,  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); }
            catch (DateTimeParseException ignore) { this.end = LocalDate.parse(endDate,  DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(); }
        }
    }

    @JsonIgnore
    public ZoneId getDefaultZoneId() {
        return defaultZoneId;
    }
}
