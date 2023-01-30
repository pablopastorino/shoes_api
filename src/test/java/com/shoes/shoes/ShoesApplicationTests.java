package com.shoes.shoes;

import com.shoes.shoes.controllers.ShoesControllerTests;
import com.shoes.shoes.repositories.ShoesRepositoryTests;
import com.shoes.shoes.service.ShoesServiceTests;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
@RunWith(Suite.class)
@Suite.SuiteClasses({
		ShoesControllerTests.class,
		ShoesRepositoryTests.class,
		ShoesServiceTests.class
})
@SpringBootTest
class ShoesApplicationTests {

	@Test
	void contextLoads() {
	}

}
