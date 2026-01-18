# Task Tracker CLI

A lightweight, robust Command Line Interface (CLI) application to track your tasks and manage your to-do list, built with Java and modern clean code principles.

## 🚀 Features

- **Task Management**: Add, update, and delete tasks.
- **Status Tracking**: Mark tasks as `todo`, `in-progress`, or `done`.
- **Persistent Storage**: Automatically saves your tasks to a local `tasks.json` file.
- **List & Filter**: View all tasks or filter them by status.
- **Clean Architecture**: Decoupled UI logic and constructor-based dependency injection for high testability.
- **Robustness**: Comprehensive Unit Test suite included.

## 🛠️ Prerequisites

- **Java 21** or higher.
- **Maven** for building and dependency management.

## 📦 Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/jeffersonvilla/task-tracker.git
   cd task_tracker
   ```

2. **Build the project**:
   ```bash
   mvn clean package
   ```

## 📖 Usage

Run the application using the generated JAR file:

```bash
java -jar target/task_tracker-1.0-SNAPSHOT.jar <command> [arguments]
```

### Supported Commands

| Command | Description | Example |
| :--- | :--- | :--- |
| `add` | Create a new task | `add "Buy groceries"` |
| `update` | Update a task description | `update 1 "Buy healthy groceries"` |
| `delete` | Remove a task | `delete 1` |
| `mark-in-progress` | Change status to in-progress | `mark-in-progress 1` |
| `mark-done` | Change status to done | `mark-done 1` |
| `list` | List all tasks | `list` |
| `list <status>` | List tasks by status | `list done` |

## 🧪 Testing

To run the automated test suite:

```bash
mvn test
```

## 📂 Project Structure

- `src/main/java`: Source code following a modular architecture.
- `src/test/java`: Unit tests for commands and persistence logic.
- `tasks.json`: Local persistence file (generated on first run).
- `logs/`: Application logs for debugging.

---
Developed by [jeffdev.online](https://jeffdev.online)
