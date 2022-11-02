import json
import click
import sonora.client
import sys
import os
from generated import NetspeakService_pb2_grpc as nspbg, NetspeakService_pb2 as nspb


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


@click.command()
@click.option("-q", "--query", required=False, type=str, default=None)
@click.option("-stdin", required=True, is_flag=True, default=False)
@click.option("-n", "--number-phrases", required=True, type=int, default=10)
def main(query, stdin, number_phrases):
    if getattr(sys, "frozen", False):
        datadir = os.path.dirname(sys.executable)
        os.environ["SSL_CERT_FILE"] = os.path.join(datadir, "cacert.pem")
        os.environ["REQUESTS_CA_BUNDLE"] = os.path.join(datadir, "cacert.pem")

    with sonora.client.insecure_web_channel("https://ngram.api.netspeak.org") as channel:
        if stdin:
            while True:
                try:
                    query = input()

                    if query == "\\exit":
                        break

                    print(json.dumps(search_request(channel, query, number_phrases)))
                except KeyboardInterrupt:
                    break

        else:
            print(json.dumps(search_request(channel, query, number_phrases)))


if __name__ == '__main__':
    main()
