package org.hmcampoverde.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
public class EntityBase implements Serializable {

	@Column(name = "deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT 'FALSE'")
	private boolean deleted;

	@Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP", updatable = false)
	private LocalDateTime createdDate;

	@Column(name = "last_modified_date", nullable = true, columnDefinition = "TIMESTAMP", insertable = false)
	private LocalDateTime lastModifiedDate;

	@PrePersist
	public void createdDate() {
		createdDate = LocalDateTime.now();
	}

	@PreUpdate
	public void updateDate() {
		lastModifiedDate = LocalDateTime.now();
	}
}
