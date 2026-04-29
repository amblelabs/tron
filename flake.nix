{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    flake-parts.url = "github:hercules-ci/flake-parts";
    systems.url = "github:nix-systems/default";
  };

  outputs = inputs:
    inputs.flake-parts.lib.mkFlake { inherit inputs; } {
      systems = import inputs.systems;

      perSystem = { config, self', pkgs, lib, system, ... }: let
        # JetBrains OpenJDK is excellent for MC modding due to DCEVM hotswapping support.
        java = pkgs.jetbrains.jdk-no-jcef;

        nativeBuildInputs = with pkgs; [
          java
          git
        ];

        # Library dependencies for Minecraft & LWJGL runtime loading
        lwjglLibraries = with pkgs; [
          libGL
          glfw
          xorg.libX11
          xorg.libXext
          xorg.libXcursor
          xorg.libXrandr
          xorg.libXxf86vm
          xorg.xrandr
          flite # TTS
          libpulseaudio # Required for audio
        ];
      in {
        devShells.default = pkgs.mkShell {
          # 1. Provide all native build tools
          nativeBuildInputs = nativeBuildInputs;

          # 2. Consolidate your project dependencies & tooling here
          buildInputs = lwjglLibraries ++ (with pkgs; [
            # Ensure the specific Java 21 LTS is available alongside JetBrains JDK if needed
            jdk21 
            jdt-language-server
            gradle
          ]);

          # 3. Environment variables
          env = {
            # LWJGL needs this path to successfully load native dynamically-linked files (.so) on NixOS
            LD_LIBRARY_PATH = lib.makeLibraryPath lwjglLibraries;
            JAVA_HOME = "${java.home}";

            # --- FIX FOR ZED JDTLS ---
            # Tells dynamically linked binaries (like Zed's lsp proxy) where the interpreter is
	    NIX_LD = "${pkgs.glibc}/lib/ld-linux-x86-64.so.2";
            
            # Appends the standard libraries needed by generic Linux binaries to your existing LD path
            NIX_LD_LIBRARY_PATH = lib.makeLibraryPath [
              pkgs.stdenv.cc.cc
              pkgs.zlib
            ];
          };
        };
      };
    };
}
