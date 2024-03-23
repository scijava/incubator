import json

test_chart = {
  "data": [
    {
      "x": [1, 2, 3, 4, 5],
      "y": [1, 2, 4, 8, 81]
    }
  ],
  "layout": {
    "margin": {
      "t": 15,
      "b": 30,
      "r": 15,
      "l": 35
    }
  }
}

with open("foo.json", "w+") as f:
    json.dump(test_chart, f)
