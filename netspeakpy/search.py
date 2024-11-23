import json
import sonora.client
import sys
import os
from netspeakpy.generated import NetspeakService_pb2_grpc as nspbg, NetspeakService_pb2 as nspb


def search_request(channel, query, n):
    stub = nspbg.NetspeakServiceStub(channel)

    search_results = stub.Search(nspb.SearchRequest(query=query, corpus="web-en", max_phrases=n)).result
    data = {"phrases": []}
    for phrase in search_results.phrases:
        phrase_data = {"id": phrase.id, "frequency": phrase.frequency, "words": []}

        for word in phrase.words:
            phrase_data["words"].append(word.text)

        data["phrases"].append(phrase_data)

    return data


def main(query, number_phrases):
    '''
    Search For query term's frequency using the netspeak API.

    Args: 
        query: str; Query to send to netspeak
        number_phrases: int; Number of max phrases.
    '''
    if getattr(sys, "frozen", False):
        datadir = os.path.dirname(sys.executable)
        os.environ["SSL_CERT_FILE"] = os.path.join(datadir, "cacert.pem")
        os.environ["REQUESTS_CA_BUNDLE"] = os.path.join(datadir, "cacert.pem")

    with sonora.client.insecure_web_channel("https://ngram.api.netspeak.org") as channel:
       
        return search_request(channel, query, number_phrases)

if __name__ == '__main__':
    main("this is a ?", 10)
