package com.example.plantforu.entity.product;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Getter
@Setter
@ToString(exclude="ctgno2")		// System.out.println(this)으로 출력할 때 무한 루프 방지
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="category_seq")
	@SequenceGenerator(name="category_seq", sequenceName="category_seq", allocationSize=1)
	@Column(name="category_no")
	private Integer ctgno;
	
	@Column(name="category_name", length=10)
	private String ctgName;

	@JsonIgnore							// JSON으로 출력할 때 무한 루프 방지
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_no_two")
	private Category ctgno2;
	
	// FetchType을 EAGER로 지정하면 대분류에 대해 left outer join한 다음 차례대로 중분류, 소분류에 대해 쿼리가 날아간다
	// 기본값인 LAZY는 로딩이 되지 않고 무조건 no session이 발생한다
	// 아마도 EAGER는 셀프조인을 모두 수행하라는 명령으로 해석, LAZY의 경우 대,중,소를 따로 읽어오는데 어디까지 읽어야할 지 범위를 지정하지 않았다로 해석하는 듯.
	@OneToMany(mappedBy = "ctgno2", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Category> categories;
	
	public void addCategory(Category child) {
		if(this.categories==null)
			this.categories = new ArrayList<>();
		child.setCtgno2(this);
		this.categories.add(child);
	}
}
