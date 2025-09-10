package com.viettridao.vaccination.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lich_tiem")
public class LichTiemEntity {

    @Id
    @GeneratedValue
    @Column(name = "ma_lich", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "tieu_de")
    private String tieuDe;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "ngay_gio", nullable = false)
    private LocalDateTime ngayGio;

    @Column(name = "dia_diem")
    private String diaDiem;

    @ManyToOne
    @JoinColumn(name = "ma_vac_xin")
    private VacXinEntity vacXin;

    @Column(name = "suc_chua")
    private Integer sucChua;

    @ManyToOne
    @JoinColumn(name = "tao_boi")
    private TaiKhoanEntity taoBoi;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @OneToMany(mappedBy = "lichTiem")
    private Set<DangKyTiemEntity> dangKyTiems;
}
