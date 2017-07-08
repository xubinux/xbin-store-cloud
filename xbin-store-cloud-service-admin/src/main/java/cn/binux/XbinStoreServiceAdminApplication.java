package cn.binux;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableTransactionManagement
@Configuration
//@EnableApolloConfig
@MapperScan(basePackages = "cn.binux.mapper")
public class XbinStoreServiceAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(XbinStoreServiceAdminApplication.class, args);
	}
}
