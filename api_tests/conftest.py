
import pytest
import requests

BASE_URL = "http://127.0.0.1:8000"

@pytest.fixture(scope="session")
def base_url():
    return BASE_URL

@pytest.fixture(scope="session")
def client():
    return requests.Session()


