package com.example.plantforu.entity;

import java.time.*;

import javax.persistence.*;

import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseCreateTimeEntity {
	@JsonFormat(pattern = "yyyy-MM-dd")
	@CreatedDate
	private LocalDateTime createTime;
}
