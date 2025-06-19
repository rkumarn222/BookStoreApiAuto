
import pytest
import requests

def test_health_check(base_url):
    response = requests.get(f"{base_url}/health")
    assert response.status_code == 200
    assert response.json() == {"status": "up"}


