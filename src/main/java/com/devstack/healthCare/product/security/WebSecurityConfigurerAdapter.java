//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.devstack.healthCare.product.security;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

/** @deprecated */
@Order(100)
@Deprecated
public abstract class WebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {
    private final Log logger;
    private ApplicationContext context;
    private ContentNegotiationStrategy contentNegotiationStrategy;
    private ObjectPostProcessor<Object> objectPostProcessor;
    private AuthenticationConfiguration authenticationConfiguration;
    private AuthenticationManagerBuilder authenticationBuilder;
    private AuthenticationManagerBuilder localConfigureAuthenticationBldr;
    private boolean disableLocalConfigureAuthenticationBldr;
    private boolean authenticationManagerInitialized;
    private AuthenticationManager authenticationManager;
    private AuthenticationTrustResolver trustResolver;
    private HttpSecurity http;
    private boolean disableDefaults;

    protected WebSecurityConfigurerAdapter() {
        this(false);
    }

    protected WebSecurityConfigurerAdapter(boolean disableDefaults) {
        this.logger = LogFactory.getLog(WebSecurityConfigurerAdapter.class);
        this.contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        this.objectPostProcessor = new ObjectPostProcessor<Object>() {
            public <T> T postProcess(T object) {
                throw new IllegalStateException(ObjectPostProcessor.class.getName() + " is a required bean. Ensure you have used @EnableWebSecurity and @Configuration");
            }
        };
        this.trustResolver = new AuthenticationTrustResolverImpl();
        this.disableDefaults = disableDefaults;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        this.disableLocalConfigureAuthenticationBldr = true;
    }

    protected final HttpSecurity getHttp() throws Exception {
        if (this.http != null) {
            return this.http;
        } else {
            AuthenticationEventPublisher eventPublisher = this.getAuthenticationEventPublisher();
            this.localConfigureAuthenticationBldr.authenticationEventPublisher(eventPublisher);
            AuthenticationManager authenticationManager = this.authenticationManager();
            this.authenticationBuilder.parentAuthenticationManager(authenticationManager);
            Map<Class<?>, Object> sharedObjects = this.createSharedObjects();
            this.http = new HttpSecurity(this.objectPostProcessor, this.authenticationBuilder, sharedObjects);
            if (!this.disableDefaults) {
                this.applyDefaultConfiguration(this.http);
                ClassLoader classLoader = this.context.getClassLoader();
                List<AbstractHttpConfigurer> defaultHttpConfigurers = SpringFactoriesLoader.loadFactories(AbstractHttpConfigurer.class, classLoader);
                Iterator var6 = defaultHttpConfigurers.iterator();

                while(var6.hasNext()) {
                    AbstractHttpConfigurer configurer = (AbstractHttpConfigurer)var6.next();
                    this.http.apply(configurer);
                }
            }

            this.configure(this.http);
            return this.http;
        }
    }

    private void applyDefaultConfiguration(HttpSecurity http) throws Exception {
        http.csrf();
        http.addFilter(new WebAsyncManagerIntegrationFilter());
        http.exceptionHandling();
        http.headers();
        http.sessionManagement();
        http.securityContext();
        http.requestCache();
        http.anonymous();
        http.servletApi();
        http.apply(new DefaultLoginPageConfigurer());
        http.logout();
    }

    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new AuthenticationManagerDelegator(this.authenticationBuilder, this.context);
    }

    protected AuthenticationManager authenticationManager() throws Exception {
        if (!this.authenticationManagerInitialized) {
            this.configure(this.localConfigureAuthenticationBldr);
            if (this.disableLocalConfigureAuthenticationBldr) {
                this.authenticationManager = this.authenticationConfiguration.getAuthenticationManager();
            } else {
                this.authenticationManager = (AuthenticationManager)this.localConfigureAuthenticationBldr.build();
            }

            this.authenticationManagerInitialized = true;
        }

        return this.authenticationManager;
    }

    public UserDetailsService userDetailsServiceBean() throws Exception {
        AuthenticationManagerBuilder globalAuthBuilder = (AuthenticationManagerBuilder)this.context.getBean(AuthenticationManagerBuilder.class);
        return new UserDetailsServiceDelegator(Arrays.asList(this.localConfigureAuthenticationBldr, globalAuthBuilder));
    }

    protected UserDetailsService userDetailsService() {
        AuthenticationManagerBuilder globalAuthBuilder = (AuthenticationManagerBuilder)this.context.getBean(AuthenticationManagerBuilder.class);
        return new UserDetailsServiceDelegator(Arrays.asList(this.localConfigureAuthenticationBldr, globalAuthBuilder));
    }

    public void init(WebSecurity web) throws Exception {
        HttpSecurity http = this.getHttp();
        web.addSecurityFilterChainBuilder(http).postBuildAction(() -> {
            FilterSecurityInterceptor securityInterceptor = (FilterSecurityInterceptor)http.getSharedObject(FilterSecurityInterceptor.class);
            web.securityInterceptor(securityInterceptor);
        });
    }

    public void configure(WebSecurity web) throws Exception {
    }

    protected void configure(HttpSecurity http) throws Exception {
        this.logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");
        http.authorizeRequests((requests) -> {
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated();
        });
        http.formLogin();
        http.httpBasic();
    }

    protected final ApplicationContext getApplicationContext() {
        return this.context;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
        ObjectPostProcessor<Object> objectPostProcessor = (ObjectPostProcessor)context.getBean(ObjectPostProcessor.class);
        LazyPasswordEncoder passwordEncoder = new LazyPasswordEncoder(context);
        this.authenticationBuilder = new DefaultPasswordEncoderAuthenticationManagerBuilder(objectPostProcessor, passwordEncoder);
        this.localConfigureAuthenticationBldr = new DefaultPasswordEncoderAuthenticationManagerBuilder(objectPostProcessor, passwordEncoder) {
            public AuthenticationManagerBuilder eraseCredentials(boolean eraseCredentials) {
                WebSecurityConfigurerAdapter.this.authenticationBuilder.eraseCredentials(eraseCredentials);
                return super.eraseCredentials(eraseCredentials);
            }

            public AuthenticationManagerBuilder authenticationEventPublisher(AuthenticationEventPublisher eventPublisher) {
                WebSecurityConfigurerAdapter.this.authenticationBuilder.authenticationEventPublisher(eventPublisher);
                return super.authenticationEventPublisher(eventPublisher);
            }
        };
    }

    @Autowired(
            required = false
    )
    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        this.trustResolver = trustResolver;
    }

    @Autowired(
            required = false
    )
    public void setContentNegotationStrategy(ContentNegotiationStrategy contentNegotiationStrategy) {
        this.contentNegotiationStrategy = contentNegotiationStrategy;
    }

    @Autowired
    public void setObjectPostProcessor(ObjectPostProcessor<Object> objectPostProcessor) {
        this.objectPostProcessor = objectPostProcessor;
    }

    @Autowired
    public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    private AuthenticationEventPublisher getAuthenticationEventPublisher() {
        return this.context.getBeanNamesForType(AuthenticationEventPublisher.class).length > 0 ? (AuthenticationEventPublisher)this.context.getBean(AuthenticationEventPublisher.class) : (AuthenticationEventPublisher)this.objectPostProcessor.postProcess(new DefaultAuthenticationEventPublisher());
    }

    private Map<Class<?>, Object> createSharedObjects() {
        Map<Class<?>, Object> sharedObjects = new HashMap();
        sharedObjects.putAll(this.localConfigureAuthenticationBldr.getSharedObjects());
        sharedObjects.put(UserDetailsService.class, this.userDetailsService());
        sharedObjects.put(ApplicationContext.class, this.context);
        sharedObjects.put(ContentNegotiationStrategy.class, this.contentNegotiationStrategy);
        sharedObjects.put(AuthenticationTrustResolver.class, this.trustResolver);
        return sharedObjects;
    }

    static class LazyPasswordEncoder implements PasswordEncoder {
        private ApplicationContext applicationContext;
        private PasswordEncoder passwordEncoder;

        LazyPasswordEncoder(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public String encode(CharSequence rawPassword) {
            return this.getPasswordEncoder().encode(rawPassword);
        }

        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return this.getPasswordEncoder().matches(rawPassword, encodedPassword);
        }

        public boolean upgradeEncoding(String encodedPassword) {
            return this.getPasswordEncoder().upgradeEncoding(encodedPassword);
        }

        private PasswordEncoder getPasswordEncoder() {
            if (this.passwordEncoder != null) {
                return this.passwordEncoder;
            } else {
                PasswordEncoder passwordEncoder = (PasswordEncoder)this.getBeanOrNull(PasswordEncoder.class);
                if (passwordEncoder == null) {
                    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                }

                this.passwordEncoder = passwordEncoder;
                return passwordEncoder;
            }
        }

        private <T> T getBeanOrNull(Class<T> type) {
            try {
                return this.applicationContext.getBean(type);
            } catch (NoSuchBeanDefinitionException var3) {
                return null;
            }
        }

        public String toString() {
            return this.getPasswordEncoder().toString();
        }
    }

    static class DefaultPasswordEncoderAuthenticationManagerBuilder extends AuthenticationManagerBuilder {
        private final PasswordEncoder defaultPasswordEncoder;

        DefaultPasswordEncoderAuthenticationManagerBuilder(ObjectPostProcessor<Object> objectPostProcessor, PasswordEncoder defaultPasswordEncoder) {
            super(objectPostProcessor);
            this.defaultPasswordEncoder = defaultPasswordEncoder;
        }

        public InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryAuthentication() throws Exception {
            return (InMemoryUserDetailsManagerConfigurer)super.inMemoryAuthentication().passwordEncoder(this.defaultPasswordEncoder);
        }

        public JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> jdbcAuthentication() throws Exception {
            return (JdbcUserDetailsManagerConfigurer)super.jdbcAuthentication().passwordEncoder(this.defaultPasswordEncoder);
        }

        public <T extends UserDetailsService> DaoAuthenticationConfigurer<AuthenticationManagerBuilder, T> userDetailsService(T userDetailsService) throws Exception {
            return (DaoAuthenticationConfigurer)super.userDetailsService(userDetailsService).passwordEncoder(this.defaultPasswordEncoder);
        }
    }

    static final class AuthenticationManagerDelegator implements AuthenticationManager {
        private AuthenticationManagerBuilder delegateBuilder;
        private AuthenticationManager delegate;
        private final Object delegateMonitor = new Object();
        private Set<String> beanNames;

        AuthenticationManagerDelegator(AuthenticationManagerBuilder delegateBuilder, ApplicationContext context) {
            Assert.notNull(delegateBuilder, "delegateBuilder cannot be null");
            Field parentAuthMgrField = ReflectionUtils.findField(AuthenticationManagerBuilder.class, "parentAuthenticationManager");
            ReflectionUtils.makeAccessible(parentAuthMgrField);
            this.beanNames = getAuthenticationManagerBeanNames(context);
            validateBeanCycle(ReflectionUtils.getField(parentAuthMgrField, delegateBuilder), this.beanNames);
            this.delegateBuilder = delegateBuilder;
        }

        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            if (this.delegate != null) {
                return this.delegate.authenticate(authentication);
            } else {
                synchronized(this.delegateMonitor) {
                    if (this.delegate == null) {
                        this.delegate = (AuthenticationManager)this.delegateBuilder.getObject();
                        this.delegateBuilder = null;
                    }
                }

                return this.delegate.authenticate(authentication);
            }
        }

        private static Set<String> getAuthenticationManagerBeanNames(ApplicationContext applicationContext) {
            String[] beanNamesForType = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, AuthenticationManager.class);
            return new HashSet(Arrays.asList(beanNamesForType));
        }

        private static void validateBeanCycle(Object auth, Set<String> beanNames) {
            if (auth != null && !beanNames.isEmpty() && auth instanceof Advised) {
                TargetSource targetSource = ((Advised)auth).getTargetSource();
                if (targetSource instanceof LazyInitTargetSource) {
                    LazyInitTargetSource lits = (LazyInitTargetSource)targetSource;
                    if (beanNames.contains(lits.getTargetBeanName())) {
                        throw new FatalBeanException("A dependency cycle was detected when trying to resolve the AuthenticationManager. Please ensure you have configured authentication.");
                    }
                }
            }
        }
    }

    static final class UserDetailsServiceDelegator implements UserDetailsService {
        private List<AuthenticationManagerBuilder> delegateBuilders;
        private UserDetailsService delegate;
        private final Object delegateMonitor = new Object();

        UserDetailsServiceDelegator(List<AuthenticationManagerBuilder> delegateBuilders) {
            Assert.isTrue(!delegateBuilders.contains((Object)null), () -> {
                return "delegateBuilders cannot contain null values. Got " + delegateBuilders;
            });
            this.delegateBuilders = delegateBuilders;
        }

        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if (this.delegate != null) {
                return this.delegate.loadUserByUsername(username);
            } else {
                synchronized(this.delegateMonitor) {
                    if (this.delegate == null) {
                        Iterator var3 = this.delegateBuilders.iterator();

                        while(var3.hasNext()) {
                            AuthenticationManagerBuilder delegateBuilder = (AuthenticationManagerBuilder)var3.next();
                            this.delegate = delegateBuilder.getDefaultUserDetailsService();
                            if (this.delegate != null) {
                                break;
                            }
                        }

                        if (this.delegate == null) {
                            throw new IllegalStateException("UserDetailsService is required.");
                        }

                        this.delegateBuilders = null;
                    }
                }

                return this.delegate.loadUserByUsername(username);
            }
        }
    }
}
