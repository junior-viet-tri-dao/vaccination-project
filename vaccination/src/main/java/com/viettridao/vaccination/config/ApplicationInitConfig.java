package com.viettridao.vaccination.config;

import java.util.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.viettridao.vaccination.model.QuyenHanEntity;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.model.VaiTroEntity;
import com.viettridao.vaccination.repository.QuyenHanRepository;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import com.viettridao.vaccination.repository.VaiTroRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig implements ApplicationRunner {

	private final VaiTroRepository vaiTroRepository;
	private final TaiKhoanRepository taiKhoanRepository;
	private final QuyenHanRepository quyenHanRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(ApplicationArguments args) {
		initPermissions();
		initRolesWithDefaultPermissions();
		initDefaultAccounts();
	}

	/**
	 * Khởi tạo danh sách quyền hệ thống
	 */
	private void initPermissions() {
	    List<QuyenHanEntity> permissions = List.of(
	        // User management
	        QuyenHanEntity.builder().ten("READ_USER").moTa("Xem thông tin người dùng").build(),
	        QuyenHanEntity.builder().ten("CREATE_USER").moTa("Tạo người dùng mới").build(),
	        QuyenHanEntity.builder().ten("UPDATE_USER").moTa("Cập nhật thông tin người dùng").build(),
	        QuyenHanEntity.builder().ten("DELETE_USER").moTa("Xóa người dùng").build(),

	        // Warehouse
	        QuyenHanEntity.builder().ten("VIEW_WAREHOUSE").moTa("Xem tình hình kho bãi").build(),
	        QuyenHanEntity.builder().ten("CREATE_IMPORT_WAREHOUSE").moTa("Nhập kho").build(),
	        QuyenHanEntity.builder().ten("CREATE_EXPORT_WAREHOUSE").moTa("Xuất kho").build(),

	        // Consultation
	        QuyenHanEntity.builder().ten("READ_CONSULTATION").moTa("Xem câu hỏi tư vấn").build(),
	        QuyenHanEntity.builder().ten("CREATE_CONSULTATION").moTa("Tạo câu hỏi tư vấn").build(),
	        QuyenHanEntity.builder().ten("UPDATE_CONSULTATION").moTa("Cập nhật câu hỏi tư vấn").build(),
	        QuyenHanEntity.builder().ten("DELETE_CONSULTATION").moTa("Xóa câu hỏi tư vấn").build(),

	        // FAQ
	        QuyenHanEntity.builder().ten("READ_FAQ").moTa("Xem thắc mắc").build(),
	        QuyenHanEntity.builder().ten("ANSWER_FAQ").moTa("Trả lời thắc mắc").build(),

	        // Reminder
	        QuyenHanEntity.builder().ten("READ_REMINDER").moTa("Xem nhắc nhở tiêm chủng").build(),
	        QuyenHanEntity.builder().ten("SEND_REMINDER").moTa("Gửi email nhắc nhở tiêm chủng").build(),

	        // Vaccine price
	        QuyenHanEntity.builder().ten("VIEW_VACCINE_PRICE").moTa("Xem giá vắc xin").build(),
	        QuyenHanEntity.builder().ten("CREATE_VACCINE_PRICE").moTa("Thêm giá vắc xin").build(),
	        QuyenHanEntity.builder().ten("UPDATE_VACCINE_PRICE").moTa("Cập nhật giá vắc xin").build(),
	        QuyenHanEntity.builder().ten("DELETE_VACCINE_PRICE").moTa("Xóa giá vắc xin").build(),

	        // Customer transaction
	        QuyenHanEntity.builder().ten("VIEW_CUSTOMER_TRANSACTION").moTa("Xem giao dịch khách hàng").build(),
	        QuyenHanEntity.builder().ten("CREATE_CUSTOMER_TRANSACTION").moTa("Tạo giao dịch khách hàng").build(),
	        QuyenHanEntity.builder().ten("UPDATE_CUSTOMER_TRANSACTION").moTa("Cập nhật giao dịch khách hàng").build(),
	        QuyenHanEntity.builder().ten("DELETE_CUSTOMER_TRANSACTION").moTa("Xóa giao dịch khách hàng").build(),

	        // Supplier transaction
	        QuyenHanEntity.builder().ten("VIEW_SUPPLIER_TRANSACTION").moTa("Xem giao dịch nhà cung cấp").build(),
	        QuyenHanEntity.builder().ten("CREATE_SUPPLIER_TRANSACTION").moTa("Tạo giao dịch nhà cung cấp").build(),
	        QuyenHanEntity.builder().ten("UPDATE_SUPPLIER_TRANSACTION").moTa("Cập nhật giao dịch nhà cung cấp").build(),
	        QuyenHanEntity.builder().ten("DELETE_SUPPLIER_TRANSACTION").moTa("Xóa giao dịch nhà cung cấp").build(),

	        // **Normal user specific**
	        QuyenHanEntity.builder().ten("VIEW_VACCINE").moTa("Xem danh sách vắc xin").build(),
	        QuyenHanEntity.builder().ten("VIEW_SCHEDULE").moTa("Xem lịch tiêm phòng").build(),
	        QuyenHanEntity.builder().ten("READ_PROFILE").moTa("Xem hồ sơ cá nhân").build(),
	        QuyenHanEntity.builder().ten("UPDATE_PROFILE").moTa("Cập nhật hồ sơ cá nhân").build(),
	        QuyenHanEntity.builder().ten("VIEW_EPIDEMIC").moTa("Xem tình hình dịch bệnh").build(),
	        QuyenHanEntity.builder().ten("SUBMIT_FEEDBACK").moTa("Gửi phản hồi").build()
	    );

	    // Chỉ insert quyền chưa tồn tại
	    List<QuyenHanEntity> newPermissions = permissions.stream()
	            .filter(p -> quyenHanRepository.findByTen(p.getTen()).isEmpty())
	            .toList();

	    if (!newPermissions.isEmpty()) {
	        quyenHanRepository.saveAll(newPermissions);
	        System.out.println(">>> Đã thêm " + newPermissions.size() + " quyền mới");
	    }
	}

	private void initRolesWithDefaultPermissions() {
		// Mapping role -> danh sách quyền
		Map<String, List<String>> rolePermissions = Map.of("ADMIN",
				quyenHanRepository.findAll().stream().map(QuyenHanEntity::getTen).toList(), "WAREHOUSE",
				List.of("VIEW_WAREHOUSE", "CREATE_IMPORT_WAREHOUSE", "CREATE_EXPORT_WAREHOUSE"), "DOCTER",
				List.of("READ_USER", "UPDATE_USER", "CREATE_USER"), "SUPPORTER",
				List.of("READ_CONSULTATION", "CREATE_CONSULTATION", "UPDATE_CONSULTATION", "DELETE_CONSULTATION",
						"READ_FAQ", "ANSWER_FAQ", "READ_REMINDER", "SEND_REMINDER"),
				"NORMAL_USER",
				List.of("VIEW_VACCINE", "VIEW_SCHEDULE", "READ_PROFILE", "UPDATE_PROFILE", "VIEW_EPIDEMIC",
						"SUBMIT_FEEDBACK", "READ_FAQ", "CREATE_CONSULTATION"),
				"FINANCE",
				List.of("VIEW_VACCINE_PRICE", "CREATE_VACCINE_PRICE", "UPDATE_VACCINE_PRICE", "DELETE_VACCINE_PRICE",
						"VIEW_CUSTOMER_TRANSACTION", "CREATE_CUSTOMER_TRANSACTION", "UPDATE_CUSTOMER_TRANSACTION",
						"DELETE_CUSTOMER_TRANSACTION", "VIEW_SUPPLIER_TRANSACTION", "CREATE_SUPPLIER_TRANSACTION",
						"UPDATE_SUPPLIER_TRANSACTION", "DELETE_SUPPLIER_TRANSACTION")

		);

		rolePermissions.forEach((roleName, permNames) -> {
			VaiTroEntity role = vaiTroRepository.findByTen(roleName).orElseGet(() -> {
				VaiTroEntity r = new VaiTroEntity();
				r.setTen(roleName);
				r.setMoTa("Mặc định cho " + roleName);
				return vaiTroRepository.save(r);
			});

			// Lấy quyền từ DB
			Set<QuyenHanEntity> perms = new HashSet<>(
					quyenHanRepository.findAll().stream().filter(p -> permNames.contains(p.getTen())).toList());

			// ADMIN thì có full quyền
			if ("ADMIN".equals(roleName)) {
				perms = new HashSet<>(quyenHanRepository.findAll());
			}

			role.setQuyenHans(perms);
			vaiTroRepository.save(role);
		});
	}

	/**
	 * Khởi tạo tài khoản mặc định
	 */
	private void initDefaultAccounts() {
	    // Tạo 10 tài khoản ADMIN
	    for (int i = 1; i <= 10; i++) {
	        createAccountIfNotExists("admin" + i, "admin123", "ADMIN", "admin" + i + "@system.com", "Nguyễn Văn Admin " + i);
	    }

	 // Tạo 10 tài khoản DOCTER
	    for (int i = 1; i <= 10; i++) {
	        createAccountIfNotExists("doctor" + i, "doctor123", "DOCTER", "doctor" + i + "@system.com", "Bác sĩ " + i);
	    }


	    // Tạo 10 tài khoản SUPPORTER
	    for (int i = 1; i <= 10; i++) {
	        createAccountIfNotExists("support" + i, "support123", "SUPPORTER", "support" + i + "@system.com", "Nhân viên hỗ trợ " + i);
	    }

	    // Tạo 10 tài khoản WAREHOUSE
	    for (int i = 1; i <= 10; i++) {
	        createAccountIfNotExists("warehouse" + i, "warehouse123", "WAREHOUSE", "warehouse" + i + "@system.com", "Nhân viên kho " + i);
	    }

	    // Tạo 10 tài khoản NORMAL_USER
	    for (int i = 1; i <= 10; i++) {
	        createAccountIfNotExists("user" + i, "user123", "NORMAL_USER", "user" + i + "@system.com", "Người dùng " + i);
	    }

	    // Tạo 10 tài khoản FINANCE
	    for (int i = 1; i <= 10; i++) {
	        createAccountIfNotExists("finance" + i, "finance123", "FINANCE", "finance" + i + "@system.com", "Kế toán " + i);
	    }
	}


	private void createAccountIfNotExists(String username, String rawPassword, String roleName, String email,
			String hoTen) {
		if (taiKhoanRepository.findByTenDangNhap(username).isEmpty()) {
			VaiTroEntity role = vaiTroRepository.findByTen(roleName)
					.orElseThrow(() -> new RuntimeException("Role " + roleName + " chưa tồn tại"));

			TaiKhoanEntity account = TaiKhoanEntity.builder().tenDangNhap(username)
					.matKhauHash(passwordEncoder.encode(rawPassword)).email(email).vaiTro(role).hoTen(hoTen)
					.isDeleted(false).hoatDong(true).ngayTao(java.time.LocalDateTime.now())
					.ngayCapNhat(java.time.LocalDateTime.now()).build();

			taiKhoanRepository.save(account);
			System.out.println(">>> Đã tạo account mặc định: " + username + " (" + roleName + ")");
		}
	}
}
