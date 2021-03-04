# Spring Security --- Authentication

常见的认证方式:
- Basic
- OAuth2

```puml
interface Authentication
abstract class AbstractAuthenticationProcessingFilter

AbstractAuthenticationProcessingFilter --> Authentication: generate

interface AuthenticationManager
class ProviderManager implements AuthenticationManager
interface AuthenticationProvider

Authentication --> AuthenticationManager: input for authentication
AuthenticationManager --> Authentication: get success response
```

Spring Security Authentication核心元素:
- AbstractAuthenticationProcessingFilter, 认证处理的入口
- SecurityContextHolder/SecurityContext, 保存认证通过的Authentication信息
- Authentication, 认证信息的抽象结构
- AuthenticationManager, 进行认证处理的接口
    + ProviderManager, AuthenticationManager的默认实现, 
    会配置一个全局的ProviderManager, 
    并为一个SecurityFilterChain会配置一个ProviderManager
    + AuthenticationProvider, 用于处理某一种认证方式

相关事件:
- AuthenticationSuccessEvent
- AbstractAuthenticationFailureEvent

## DaoAuthenticationProvider For username/password authentication

通过***用户名***获取UserDetails(包含密码信息), 并对其中的***密码***进行校验的认证方式. 
常用于Form认证和Basic认证等.

```puml
abstract class AbstractUserDetailsAuthenticationProvider implements AuthenticationProvider
class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider

interface UserDetails {
    String getPassword();
    String getUsername();
}
interface UserDetailsService {
    UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException;
}
UserDetailsService --> UserDetails: get

DaoAuthenticationProvider "1" *--> "1" UserDetailsService
```

- UserDetailsService
- UserDetails, 包含用户名, 密码和其他用户信息.
- PasswordEncoder, 对密码进行加密显示
- UsernamePasswordAuthenticationToken, 认证输入或者是认证成功后的Authentication信息
- UsernamePasswordAuthenticationFilter, 用于Form认证
- BasicAuthenticationFilter, 用于Basic认证

## 第三方服务进行校验的认证

第三方服务的用户输入的用户名和密码进行校验, 校验成功后返回用户信息(不包含密码), 
无法使用DaoAuthenticationProvider和UserDetails, 例如LDAP.

### Pre-Authentication

预校验的场景:
- X.509
- 微信

核心元素:
- AbstractPreAuthenticatedProcessingFilter, Pre-Authentication的入口
- PreAuthenticatedAuthenticationToken, Pre-Authentication的Authentication
- PreAuthenticatedAuthenticationProvider, Pre-Authentication的校验器
- AuthenticationUserDetailsService, 通过Authentication信息

## Session管理

- SessionManagementFilter
- SessionAuthenticationStrategy, 
在SessionManagementFilter和AbstractAuthenticationProcessingFilter使用
- SessionRegistry

- 可以限定一个用户的同时登陆数

## Logout

通过WebSecurityConfigurerAdapter#configure(HttpSecurity http)来进行配置

- LogoutHandler, 进行登出的相关信息的清理
- LogoutSuccessHandler


## Reference

- [Servlet Authentication](https://docs.spring.io/spring-security/site/docs/5.4.2/reference/html5/#servlet-authentication)