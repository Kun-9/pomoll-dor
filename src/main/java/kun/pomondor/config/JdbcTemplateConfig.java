package kun.pomondor.config;

import kun.pomondor.repository.member.MemberRepository;
import kun.pomondor.repository.member.MemberRepositoryImpl;
import kun.pomondor.repository.time.TimeRepository;
import kun.pomondor.repository.time.TimeRepositoryImpl;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.service.member.MemberServiceImpl;
import kun.pomondor.service.time.TimeService;
import kun.pomondor.service.time.TimeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateConfig {

	private final DataSource dataSource;

	@Bean
	public MemberService memberService() {
		return new MemberServiceImpl(memberRepository());
	}

	@Bean
	public MemberRepository memberRepository() {
		return new MemberRepositoryImpl(dataSource);
	}

	@Bean
	public TimeRepository timeRepository() {
		return new TimeRepositoryImpl(dataSource);
	}

	@Bean
	public TimeService timeService() {
		return new TimeServiceImpl(timeRepository());
	}
}

