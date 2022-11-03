SHELL := /bin/bash

protoc:
	mkdir -p src/main/python/generated
	protoc --python_out=src/main/python/generated NetspeakService.proto

python: protoc
	python3.10 -m venv src/main/python/venv
	cd src/main/python && source venv/bin/activate && python -m pip install -r requirements.txt
	cd src/main/python && source venv/bin/activate && python setup.py build && chmod +x build/exe.linux-x86_64-3.10/netspeak_search_wrapper
	cd src/main/python && zip -r build/py3.10-linux-x86_64.zip build/exe.linux-x86_64-3.10/ && cp -p build/py3.10-linux-x86_64.zip /mnt/ceph/storage/web/files/data-in-production/data-research/netspeak/netspeak-client

java:
	mvn clean compile
	mvn package assembly:single

clean:
	rm -rf src/main/python/generated/NetspeakService_pb2.py
	rm -rf src/main/python/venv
	rm -rf src/main/python/build
	rm -rf python-netspeak-client.zip
	rm -rf build
	mvn clean

build: clean protoc python java