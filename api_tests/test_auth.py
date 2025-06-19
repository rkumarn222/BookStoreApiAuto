
import pytest
import requests
import time

@pytest.fixture(scope="module")
def register_and_login_user(base_url, client):
    # Register a new user with a unique email
    unique_email = f"testuser_{int(time.time())}@example.com"
    signup_data = {"email": unique_email, "password": "testpassword"}
    signup_response = client.post(f"{base_url}/signup", json=signup_data)
    assert signup_response.status_code == 200
    assert signup_response.json() == {"message": "User created successfully"}

    # Log in the user
    login_data = {"email": unique_email, "password": "testpassword"}
    login_response = client.post(f"{base_url}/login", json=login_data)
    assert login_response.status_code == 200
    token = login_response.json()["access_token"]
    assert token is not None
    return token

def test_user_signup(base_url, client):
    unique_email = f"newuser_{int(time.time())}@example.com"
    signup_data = {"email": unique_email, "password": "newpassword"}
    response = client.post(f"{base_url}/signup", json=signup_data)
    assert response.status_code == 200
    assert response.json() == {"message": "User created successfully"}

def test_user_login(base_url, client):
    # First, ensure a user exists to log in with a unique email
    unique_email = f"loginuser_{int(time.time())}@example.com"
    signup_data = {"email": unique_email, "password": "loginpassword"}
    client.post(f"{base_url}/signup", json=signup_data)

    login_data = {"email": unique_email, "password": "loginpassword"}
    response = client.post(f"{base_url}/login", json=login_data)
    assert response.status_code == 200
    assert "access_token" in response.json()

def test_authenticated_access(base_url, client, register_and_login_user):
    token = register_and_login_user
    headers = {"Authorization": f"Bearer {token}"}
    response = client.get(f"{base_url}/books/", headers=headers)
    assert response.status_code == 200
    assert isinstance(response.json(), list)


