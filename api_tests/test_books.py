
import pytest
import requests

@pytest.fixture(scope="module")
def auth_headers(base_url, client):
    signup_data = {"email": "bookuser@example.com", "password": "bookpassword"}
    client.post(f"{base_url}/signup", json=signup_data)
    login_data = {"email": "bookuser@example.com", "password": "bookpassword"}
    response = client.post(f"{base_url}/login", json=login_data)
    token = response.json()["access_token"]
    return {"Authorization": f"Bearer {token}"}

def test_create_book(base_url, client, auth_headers):
    book_data = {"name": "Test Book", "author": "Test Author", "published_year": 2023, "book_summary": "A summary of the test book."}
    response = client.post(f"{base_url}/books/", json=book_data, headers=auth_headers)
    assert response.status_code == 200
    assert response.json()["name"] == "Test Book"
    assert response.json()["author"] == "Test Author"
    assert response.json()["published_year"] == 2023

def test_get_all_books(base_url, client, auth_headers):
    response = client.get(f"{base_url}/books/", headers=auth_headers)
    assert response.status_code == 200
    assert isinstance(response.json(), list)

def test_get_book_by_id(base_url, client, auth_headers):
    # Create a book first to get its ID
    book_data = {"name": "Book to Get", "author": "Author to Get", "published_year": 2020, "book_summary": "Summary for book to get."}
    create_response = client.post(f"{base_url}/books/", json=book_data, headers=auth_headers)
    book_id = create_response.json()["id"]

    response = client.get(f"{base_url}/books/{book_id}", headers=auth_headers)
    assert response.status_code == 200
    assert response.json()["name"] == "Book to Get"

def test_update_book(base_url, client, auth_headers):
    # Create a book first to get its ID
    book_data = {"name": "Book to Update", "author": "Author to Update", "published_year": 2019, "book_summary": "Summary for book to update."}
    create_response = client.post(f"{base_url}/books/", json=book_data, headers=auth_headers)
    book_id = create_response.json()["id"]

    updated_data = {"name": "Updated Book", "author": "Updated Author", "published_year": 2021, "book_summary": "Updated summary."}
    response = client.put(f"{base_url}/books/{book_id}", json=updated_data, headers=auth_headers)
    assert response.status_code == 200
    assert response.json()["name"] == "Updated Book"
    assert response.json()["author"] == "Updated Author"
    assert response.json()["published_year"] == 2021

def test_delete_book(base_url, client, auth_headers):
    # Create a book first to get its ID
    book_data = {"name": "Book to Delete", "author": "Author to Delete", "published_year": 2018, "book_summary": "Summary for book to delete."}
    create_response = client.post(f"{base_url}/books/", json=book_data, headers=auth_headers)
    book_id = create_response.json()["id"]

    response = client.delete(f"{base_url}/books/{book_id}", headers=auth_headers)
    assert response.status_code == 200
    assert response.json() == {"message": "Book deleted successfully"}

    # Verify the book is deleted
    get_response = client.get(f"{base_url}/books/{book_id}", headers=auth_headers)
    assert get_response.status_code == 404


