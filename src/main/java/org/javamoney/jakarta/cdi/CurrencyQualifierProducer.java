/*
 * Copyright (c) 2022, Otavio Santana and others by the @author tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package org.javamoney.jakarta.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.Annotated;
import jakarta.enterprise.inject.spi.InjectionPoint;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

@ApplicationScoped
class CurrencyQualifierProducer {

    @Produces
    @Currency("")
    CurrencyUnit getCurrency(InjectionPoint injectionPoint) {
        Annotated annotated = injectionPoint.getAnnotated();
        Currency currency = annotated.getAnnotation(Currency.class);
        return Monetary.getCurrency(currency.value());
    }
}
