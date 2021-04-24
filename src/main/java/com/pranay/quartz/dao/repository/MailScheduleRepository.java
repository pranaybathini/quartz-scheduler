package com.pranay.quartz.dao.repository;

import com.pranay.quartz.models.entity.MailSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailScheduleRepository extends JpaRepository<MailSchedule, Integer> {

    public Optional<MailSchedule> findByScheduleIdAndIsDeletedFalse(Integer scheduleId);

    public Page<MailSchedule> findByUsernameAndIsDeletedFalse(String username, Pageable pageable);

    public boolean existsByUsernameAndScheduleIdAndIsDeletedFalse(String username, Integer scheduleId);


}
