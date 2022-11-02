from cx_Freeze import setup, Executable
import certifi

options = {
    "build_exe": {
        "include_files": [(certifi.where(), "cacert.pem")]
    }
}

setup(
    name="netspeak-client",
    version="1.0",
    description="GRPC Client for the Netspeak API",
    options=options,
    executables=[Executable("netspeak_search_wrapper.py")]
)
