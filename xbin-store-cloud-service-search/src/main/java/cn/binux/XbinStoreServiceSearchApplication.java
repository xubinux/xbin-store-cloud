package cn.binux;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableHystrix
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "cn.binux.search.mapper")
public class XbinStoreServiceSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(XbinStoreServiceSearchApplication.class, args);

	}
}
