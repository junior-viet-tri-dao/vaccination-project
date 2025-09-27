package com.viettridao.vaccination.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	@GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_lich",columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "tieu_de")
    private String tieuDe;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "ngay_gio")
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

    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "lichTiem")
    private Set<DangKyTiemEntity> dangKyTiems;
    
    @OneToMany(mappedBy = "lichTiem")
    private List<KetQuaTiemEntity> ketQuaTiems;
    
    @OneToMany(mappedBy = "lichTiem")
    private List<DonThuocEntity> danhSachDonThuoc;
    
    @ManyToMany
    @JoinTable(
        name = "lich_tiem_bac_si",
        joinColumns = @JoinColumn(name = "lich_tiem_id"),
        inverseJoinColumns = @JoinColumn(name = "tai_khoan_id")
    )
    private Set<TaiKhoanEntity> bacSis = new HashSet<>();

}
