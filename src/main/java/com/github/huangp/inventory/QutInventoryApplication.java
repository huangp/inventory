package com.github.huangp.inventory;

import com.github.huangp.inventory.repository.RepositoryPackageMarker;
import com.github.huangp.inventory.web.WebPackageMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {RepositoryPackageMarker.class, WebPackageMarker.class})
public class QutInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(QutInventoryApplication.class, args);
	}

}
