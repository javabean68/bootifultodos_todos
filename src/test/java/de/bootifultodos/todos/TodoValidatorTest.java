/*
 * Copyright 2017 michael-simons.eu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.bootifultodos.todos;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * @author Michael J. Simons, 2017-04-05
 */
public class TodoValidatorTest {

	@Test
	public void supportsShouldWork() {
		final TodoValidator todoValidator = new TodoValidator();
		assertThat(todoValidator.supports(Todo.class)).isTrue();
		assertThat(todoValidator.supports(TodoValidator.class)).isFalse();
	}

	@Test
	public void validateWithValidTodoShouldWork() {
		final TodoValidator todoValidator = new TodoValidator();

		final Todo validTodo = new Todo();
		validTodo.setAufgabe("Foobar");
		validTodo.setStatus(Todo.Status.OFFEN);

		Errors errors = new BeanPropertyBindingResult(validTodo, "todo");
		todoValidator.validate(validTodo, errors);

		assertThat(errors.hasErrors()).isFalse();
	}

	@Test
	public void validateWithInValidTodoShouldWork() {
		final TodoValidator todoValidator = new TodoValidator();
		
		final Todo invalidTodo = new Todo();
		Errors errors = new BeanPropertyBindingResult(invalidTodo, "todo");
		todoValidator.validate(invalidTodo, errors);

		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("aufgabe").getCode()).isEqualTo("todo.aufgabe.empty");
		assertThat(errors.hasFieldErrors("status")).isFalse();
	}
}
