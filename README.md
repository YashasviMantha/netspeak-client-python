# Netspeak Python Client

FULL CREDITS TO THE COOL FOLKS AT: https://github.com/netspeak/netspeak-client-java

Netspeak already offers a [Java Client](https://github.com/netspeak/netspeak-client-java); But this Client is essentially a wrapper over a Python wrapper. 

This repo is where I remove the Java stuff and refactored code to make it a pip installable package. It was just eaiser to call a python function directly in my notebook over calling a java function which again calls a python function anyway. 

Please visit the netspeak guys for more information.

## Usage
First build the dist the file and install the package:
```
# clone the repo
git clone https://github.com/YashasviMantha/netspeak-client-python
cd netspeak-client-python

# in a new virtual env: python 3.10
pip install -r requirements.txt

python setup.py sdist
pip install dist/[THE GENERATED DIST FILE]
```

Then you can use it like this:
```
from netspeakpy import search
search.main(query="this is a ?", number_phrases=10)
```

Output: 
```
{'phrases': [{'id': 18151945758,
   'frequency': 2896773,
   'words': ['this', 'is', 'a', 'very']},
  {'id': 17517024508,
   'frequency': 2750230,
   'words': ['this', 'is', 'a', 'great']},
  {'id': 17770194403,
   'frequency': 1765495,
   'words': ['this', 'is', 'a', 'good']},
  {'id': 17660424855,
   'frequency': 817066,
   'words': ['this', 'is', 'a', 'new']},
  {'id': 18092616399,
   'frequency': 675151,
   'words': ['this', 'is', 'a', 'normal']},
  {'id': 17953164472,
   'frequency': 502291,
   'words': ['this', 'is', 'a', 'list']},
  {'id': 17929435974,
   'frequency': 499116,
   'words': ['this', 'is', 'a', 'link']},
  {'id': 17404254150,
   'frequency': 486311,
   'words': ['this', 'is', 'a', 'level']},
  {'id': 17899766786,
   'frequency': 419393,
   'words': ['this', 'is', 'a', 'free']},
  {'id': 17516035039,
   'frequency': 413284,
   'words': ['this', 'is', 'a', 'must']}]}
```


