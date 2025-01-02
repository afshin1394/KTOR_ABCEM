# DefaultApi

All URIs are relative to *http://192.168.50.120:80*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**apiAuthorizationIpInfoGet**](DefaultApi.md#apiAuthorizationIpInfoGet) | **GET** /api/authorization/ipInfo |  |
| [**apiUsersCreatePost**](DefaultApi.md#apiUsersCreatePost) | **POST** /api/users/create |  |
| [**apiUsersGetAllGet**](DefaultApi.md#apiUsersGetAllGet) | **GET** /api/users/getAll |  |
| [**apiUsersInsertAllPost**](DefaultApi.md#apiUsersInsertAllPost) | **POST** /api/users/insertAll |  |
| [**sessionIncrementGet**](DefaultApi.md#sessionIncrementGet) | **GET** /session/increment |  |


<a id="apiAuthorizationIpInfoGet"></a>
# **apiAuthorizationIpInfoGet**
> IpInfoDTO apiAuthorizationIpInfoGet()





### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://192.168.50.120:80");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      IpInfoDTO result = apiInstance.apiAuthorizationIpInfoGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#apiAuthorizationIpInfoGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**IpInfoDTO**](IpInfoDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **404** | Not Found |  -  |

<a id="apiUsersCreatePost"></a>
# **apiUsersCreatePost**
> String apiUsersCreatePost(userDTO)





### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://192.168.50.120:80");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    UserDTO userDTO = new UserDTO(); // UserDTO | 
    try {
      String result = apiInstance.apiUsersCreatePost(userDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#apiUsersCreatePost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userDTO** | [**UserDTO**](UserDTO.md)|  | |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

<a id="apiUsersGetAllGet"></a>
# **apiUsersGetAllGet**
> List&lt;UserDTO&gt; apiUsersGetAllGet()





### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://192.168.50.120:80");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      List<UserDTO> result = apiInstance.apiUsersGetAllGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#apiUsersGetAllGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;UserDTO&gt;**](UserDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a id="apiUsersInsertAllPost"></a>
# **apiUsersInsertAllPost**
> String apiUsersInsertAllPost(userDTO)





### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://192.168.50.120:80");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    List<UserDTO> userDTO = Arrays.asList(); // List<UserDTO> | 
    try {
      String result = apiInstance.apiUsersInsertAllPost(userDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#apiUsersInsertAllPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userDTO** | [**List&lt;UserDTO&gt;**](UserDTO.md)|  | |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |
| **400** | Bad Request |  -  |

<a id="sessionIncrementGet"></a>
# **sessionIncrementGet**
> String sessionIncrementGet()





### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://192.168.50.120:80");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      String result = apiInstance.sessionIncrementGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#sessionIncrementGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

