# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

import netspeakpy.generated.NetspeakService_pb2 as NetspeakService__pb2


class NetspeakServiceStub(object):
    """Missing associated documentation comment in .proto file."""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.Search = channel.unary_unary(
                '/netspeak.service.NetspeakService/Search',
                request_serializer=NetspeakService__pb2.SearchRequest.SerializeToString,
                response_deserializer=NetspeakService__pb2.SearchResponse.FromString,
                )
        self.GetCorpora = channel.unary_unary(
                '/netspeak.service.NetspeakService/GetCorpora',
                request_serializer=NetspeakService__pb2.CorporaRequest.SerializeToString,
                response_deserializer=NetspeakService__pb2.CorporaResponse.FromString,
                )


class NetspeakServiceServicer(object):
    """Missing associated documentation comment in .proto file."""

    def Search(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def GetCorpora(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_NetspeakServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'Search': grpc.unary_unary_rpc_method_handler(
                    servicer.Search,
                    request_deserializer=NetspeakService__pb2.SearchRequest.FromString,
                    response_serializer=NetspeakService__pb2.SearchResponse.SerializeToString,
            ),
            'GetCorpora': grpc.unary_unary_rpc_method_handler(
                    servicer.GetCorpora,
                    request_deserializer=NetspeakService__pb2.CorporaRequest.FromString,
                    response_serializer=NetspeakService__pb2.CorporaResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'netspeak.service.NetspeakService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class NetspeakService(object):
    """Missing associated documentation comment in .proto file."""

    @staticmethod
    def Search(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/netspeak.service.NetspeakService/Search',
            NetspeakService__pb2.SearchRequest.SerializeToString,
            NetspeakService__pb2.SearchResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def GetCorpora(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/netspeak.service.NetspeakService/GetCorpora',
            NetspeakService__pb2.CorporaRequest.SerializeToString,
            NetspeakService__pb2.CorporaResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)
