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

package org.javamoney.jakarta.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CurrencyAcceptedValidatorTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private CurrencyAcceptedValidator currencyValidator;

	@Mock
	private CurrencyAccepted constraintAnnotation;

	@Mock
	private ConstraintValidatorContext context;

	@BeforeEach
	public void setup() {

		currencyValidator = new CurrencyAcceptedValidator();
	}


	@Test
	public void shouldReturnsTrueWhenCurrencyIsNull() {
		assertTrue(currencyValidator.isValid(null, context));
	}

	@Test
	public void shouldReturnsTrueWhenCurrencyIsAllowed() {
		when(constraintAnnotation.currencies()).thenReturn(new String[0]);
		when(constraintAnnotation.currenciesFromLocales()).thenReturn(new String[0]);
		String currencyCodAllowed = "USD";
		when(constraintAnnotation.currencies()).thenReturn(new String[]{currencyCodAllowed});
		currencyValidator.initialize(constraintAnnotation);
		assertTrue(currencyValidator.isValid(Monetary.getCurrency(currencyCodAllowed), context));
	}

	@Test
	public void shouldReturnsFalseWhenCurrencyIsDenied() {
		when(constraintAnnotation.currencies()).thenReturn(new String[0]);
		when(constraintAnnotation.currenciesFromLocales()).thenReturn(new String[0]);
		String currencyCodAllowed = "USD";
		when(constraintAnnotation.currencies()).thenReturn(new String[]{currencyCodAllowed});
		currencyValidator.initialize(constraintAnnotation);
		assertFalse(currencyValidator.isValid(Monetary.getCurrency("EUR"), context));
	}

	  @Test
	   public void shouldReturnsEmptyConstrainsWhenCurrencyIsNull(){
		   CurrencyValidator currency = new CurrencyValidator(null);
		   Set<ConstraintViolation<CurrencyValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	   }

	   @Test
	   public void shouldReturnsEmptyConstrainsWhenCurrencyIsAllowed(){
		   CurrencyValidator currency = new CurrencyValidator(Monetary.getCurrency("BRL"));
		   Set<ConstraintViolation<CurrencyValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	   }


	   @Test
	   public void shouldReturnsConstrainsWhenCurrencyDenied(){
		   CurrencyValidator currency = new CurrencyValidator(Monetary.getCurrency(Locale.US));
		   Set<ConstraintViolation<CurrencyValidator>> constraintViolations =
				      validator.validate(currency);

		   assertEquals(1, constraintViolations.size());
		   assertEquals("{org.javamoney.jakarta.validator.currency.accepted}", constraintViolations.iterator().next().getMessageTemplate());
	   }

	private static class CurrencyValidator {

		@CurrencyAccepted(currencies = "BRL")
		private final CurrencyUnit currencyUnit;

		CurrencyValidator(CurrencyUnit currencyUnit) {
			this.currencyUnit = currencyUnit;
		}
	}

}