# Java Task Manager

A plain Java task management application that supports CRUD operations, task validation, API-style responses, and natural-language task creation.

## Features
- Create, retrieve, list, and delete tasks.
- Immutable Task model using the Builder design pattern.
- In-memory task repository.
- Task validation for title, description, completion status, due date, and priority.
- Structured API-style success and error responses.
- Exception handling for validation errors and missing tasks.
- Natural-language task creation using a parser interface.
- Rule-based offline parser for relative dates, priorities, completion status, and recurrence.
- OpenAI integration using the Responses API.

## Technology Stack

- Java (JDK 17+)
- Java HttpClient
- Java Time API
- IntelliJ IDEA
- Git and GitHub

## Project Structure

```text
src/
├── api/
├── app/
├── event/
├── exception/
├── model/
├── parser/
├── repository/
└── service/
```

## How to Run

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Configure JDK 17 or later.
4. Open `src/app/Main.java`.
5. Run the Main class.

The application uses an in-memory repository and does not require a database.

## Natural-Language Task Creation

Example input:

```text
Complete Java assignment tomorrow. High priority.
```

The rule-based parser extracts:
- Task description
- Due date
- Priority
- Completion status
- Recurrence where supported

The offline parser works without an API key.

## OpenAI Integration

The project also includes an OpenAI-based task parser.

To use it, configure the `LLM_API_KEY` environment variable with a valid OpenAI API key and ensure API billing is available.

If the key is absent, the parser factory selects the offline rule-based parser.

## Current Limitations

- Tasks are stored in memory and are not persistent after application restart.
- OpenAI API execution requires available API credits.
- Idempotency protection and automatic subtask generation are not yet fully implemented.

## Author


Vishuddha Patil
