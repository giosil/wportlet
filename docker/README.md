# WPortlet - Docker

Here are some examples to containerize liferay starting from a linux distribution.

However, it is recommended to use the official images and to use custom images when necessary.

## Build container

`docker build -t demo-liferay:1.0.0 .`

## Start compose

`docker compose -p "demo-cluster" up --detach`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
