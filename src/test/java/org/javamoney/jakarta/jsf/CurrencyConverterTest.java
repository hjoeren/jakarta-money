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
package org.javamoney.jakarta.jsf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CurrencyConverterTest {
	
	private CurrencyConverter converter;

	private FacesContext context;

	private UIComponent component;
	
	private final String currencyCode = "BRL";
	
	@BeforeEach
	public void init() {
		converter = new CurrencyConverter();
	}
	
	@Test
	public void shouldReturnsNullWhenStringIsNull() {
		Object result = converter.getAsObject(context, component, null);
		assertNull(result);
	}
	
	@Test
	public void shouldReturnsCurrencyWhenHasCurrencyCode() {
		CurrencyUnit result = converter.getAsObject(context, component, currencyCode);
		assertNotNull(result);
		CurrencyUnit currency = result;
		assertEquals(Monetary.getCurrency(currencyCode), currency);
	}
	
	
	@Test
	public void shouldReturnsNullWhenObjectIsNull() {
		Object result = converter.getAsString(context, component, null);
		assertNull(result);
	}
	
	@Test
	public void shouldReturnsCurrencyCodeWhenHasCurrency() {
		CurrencyUnit expectedCurrency = Monetary.getCurrency(currencyCode);
		String result = converter.getAsString(context, component, expectedCurrency);
		assertNotNull(result);
		assertEquals(Monetary.getCurrency(currencyCode).toString(), result);
	}

}
