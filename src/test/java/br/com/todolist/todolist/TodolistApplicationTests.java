package br.com.todolist.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static br.com.todolist.todolist.TestConstants.TODO;
import static br.com.todolist.todolist.TestConstants.TODOS;
import br.com.todolist.todolist.entity.Todo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("/remove.sql")
class TodolistApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testCreateTodoSuccess() {
		var todo = new Todo("todo 1", "desc todo 1", false, 1);

		webTestClient
			.post()
			.uri("/todos")
			.bodyValue(todo)
			.exchange()
			.expectStatus().isCreated()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(1)
			.jsonPath("$[0].name").isEqualTo(todo.getName())
			.jsonPath("$[0].description").isEqualTo(todo.getDescription())
			.jsonPath("$[0].done").isEqualTo(todo.isDone())
			.jsonPath("$[0].priority").isEqualTo(todo.getPriority());
	}

	@Test
	public void testCreateTodoFailure() {
		var todo = new Todo("", "", false, 1);

		webTestClient
			.post()
			.uri("/todos")
			.bodyValue(todo)
			.exchange()
			.expectStatus().isBadRequest();
	}

	@Sql("/import.sql")
	@Test
	public void testUpdateTodoSuccess() {
		var todo = new Todo(TODO.getId(), TODO.getName(), TODO.getDescription() + " Up", TODO.isDone(), TODO.getPriority() + 1);

		webTestClient
			.put()
			.uri("/todos/" + todo.getId())
			.bodyValue(todo)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(5)
			.jsonPath("$[0].name").isEqualTo(todo.getName())
			.jsonPath("$[0].description").isEqualTo(todo.getDescription())
			.jsonPath("$[0].done").isEqualTo(todo.isDone())
			.jsonPath("$[0].priority").isEqualTo(todo.getPriority());
	}

	@Test
	public void testUpdateTodoFailure() {
		var unexinstingId = 1L;

		webTestClient
			.put()
			.uri("/todos/" + unexinstingId)
			.bodyValue(
				new Todo(unexinstingId, "", "", false, 0))
			.exchange()
			.expectStatus().isBadRequest();
	}

	@Sql("/import.sql")
	@Test
	public void testDeleteTodoSuccess() {
		webTestClient
			.delete()
			.uri("/todos/" + TODOS.get(0).getId())
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(4)
			.jsonPath("$[0].name").isEqualTo(TODOS.get(1).getName())
			.jsonPath("$[0].description").isEqualTo(TODOS.get(1).getDescription())
			.jsonPath("$[0].done").isEqualTo(TODOS.get(1).isDone())
			.jsonPath("$[0].priority").isEqualTo(TODOS.get(1).getPriority());
		}

	@Test
	public void testDeleteTodoFailure() {
		var unexinstingId = 1L;
		
		webTestClient
			.delete()
			.uri("/todos/" + unexinstingId)
			.exchange()
			.expectStatus().isBadRequest();
		}

	@Sql("/import.sql")
	@Test
	public void testListTodos() throws Exception {
		webTestClient
			.get()
			.uri("/todos")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(5)
			.jsonPath("$[0].id").isEqualTo(TODOS.get(0).getId())
			.jsonPath("$[1].id").isEqualTo(TODOS.get(1).getId())
			.jsonPath("$[2].id").isEqualTo(TODOS.get(2).getId())
			.jsonPath("$[3].id").isEqualTo(TODOS.get(3).getId())
			.jsonPath("$[4].id").isEqualTo(TODOS.get(4).getId());
		}
}
