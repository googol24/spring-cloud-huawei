/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huaweicloud.common.transport;

import com.huaweicloud.common.auth.AuthHeaderUtils;

import java.util.Map;

import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * @Author wangqijun
 * @Date 18:44 2019-09-05
 **/
public class DealHeaderUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(DealHeaderUtil.class);

  public static final int CONNECT_TIMEOUT = 5000;

  public static final int CONNECTION_REQUEST_TIMEOUT = 5000;

  public static final int LONG_POLLING_SOCKET_TIMEOUT = 50000;

  public static final int SOCKET_TIMEOUT = 5000;

  public static final int MAX_TOTAL = 1000;

  public static final int DEFAULT_MAX_PER_ROUTE = 500;

  public static final String X_DOMAIN_NAME = "x-domain-name";

  public static final String DEFAULT_X_DOMAIN_NAME = "default";

  public static final String CONTENT_TYPE = "Content-type";

  public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

  public static final String X_SERVICE_AK = "X-Service-AK";

  public static final String X_SERVICE_SHA_AKSK = "X-Service-ShaAKSK";

  public static final String X_SERVICE_PROJECT = "X-Service-Project";

  public static void addAKSKHeader(HttpUriRequest httpRequest,
      ServiceCombAkSkProperties serviceCombAkSkProperties) {
    Map<String, String> headerMap = AuthHeaderUtils.genAuthHeaders();
    if ((serviceCombAkSkProperties == null || serviceCombAkSkProperties.isAkSkEmpty())
        && !CollectionUtils.isEmpty(headerMap)) {
      LOGGER.warn("please configure ak/sk manually, we will offline automatic read ak/sk soon!");
      httpRequest.addHeader(X_SERVICE_AK, headerMap.get(X_SERVICE_AK));
      httpRequest.addHeader(X_SERVICE_SHA_AKSK, headerMap.get(X_SERVICE_SHA_AKSK));
    } else if (serviceCombAkSkProperties != null && !serviceCombAkSkProperties.isAkSkEmpty()) {
      httpRequest.addHeader(X_SERVICE_AK, serviceCombAkSkProperties.getAccessKey());
      httpRequest.addHeader(X_SERVICE_SHA_AKSK, serviceCombAkSkProperties.getSecretKey());
    }
    if (!CollectionUtils.isEmpty(headerMap)) {
      httpRequest.addHeader(X_SERVICE_PROJECT, headerMap.get(X_SERVICE_PROJECT));
    } else if (serviceCombAkSkProperties != null && !serviceCombAkSkProperties.isProjectEmpty()) {
      httpRequest.addHeader(X_SERVICE_PROJECT, serviceCombAkSkProperties.getProject());
    }
  }

  public static void addDefautHeader(HttpUriRequest httpRequest) {
    httpRequest.addHeader(X_DOMAIN_NAME, DEFAULT_X_DOMAIN_NAME);
    httpRequest.addHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON);
  }
}
