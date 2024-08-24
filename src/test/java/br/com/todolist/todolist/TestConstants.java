package br.com.todolist.todolist;

import java.util.ArrayList;
import java.util.List;

import br.com.todolist.todolist.entity.Todo;

public class TestConstants {
    public static final List<Todo> TODOS = new ArrayList<>() {
        {
            add(new Todo(9995L, "@jalesfonsc", "Curtir", false, 1));
            add(new Todo(9996L, "@jalesfonsc", "Comentar", false, 1));
            add(new Todo(9997L, "@jalesfonsc", "Compartilhar", false, 1));
            add(new Todo(9998L, "@jalesfonsc", "Inscrever-se", false, 1));
            add(new Todo(9999L, "@jalesfonsc", "Ativar as notificações", false, 1));
        }
    };

    public static final Todo TODO = TODOS.get(0);
}
