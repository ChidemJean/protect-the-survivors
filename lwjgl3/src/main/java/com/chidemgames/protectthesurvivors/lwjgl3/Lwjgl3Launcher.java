package com.chidemgames.protectthesurvivors.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.chidemgames.protectthesurvivors.Main;
import com.chidemgames.protectthesurvivors.PTSGame;
import com.chidemgames.protectthesurvivors.callbacks.IChangeSpeechRecognizerCallback;
import com.chidemgames.protectthesurvivors.callbacks.IRequestActionCallback;
import com.chidemgames.protectthesurvivors.entities.Cena;
import com.chidemgames.protectthesurvivors.entities.Jogo;
import com.chidemgames.protectthesurvivors.entities.Objeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        PTSGame game = new PTSGame();
        game.setRequestActionListener(new IRequestActionCallback() {
            @Override
            public int atualizarJogador(String token, String nome, String email, String senha, String tipo, int moeda) {
                return 0;
            }

            @Override
            public int salvarJogo(String token, String nome, int xp, int pontuacao, int level, String tipoJogo) {
                return 0;
            }

            @Override
            public List<Jogo> buscarJogos(String nome) {
                return null;
            }

            @Override
            public int removerJogo(String token, int id) {
                return 0;
            }

            @Override
            public int updateJogo(String token, int id, int xp, int pontuacao, int level) {
                return 0;
            }

            @Override
            public int atualizarCena(String token, int id, String tipo, int pontuacao, boolean isStateSaved) {
                return 0;
            }

            @Override
            public List<Cena> buscarCenas(String token, String nome) {
                return null;
            }

            @Override
            public List<Objeto> buscarObjetos(String token, int id) {
                return null;
            }

            @Override
            public int salvarOjetos(String token, List<Objeto> objetos, int idCena) {
                return 0;
            }

            @Override
            public int removerObjetos(String token, List<Objeto> objetos, int idCena) {
                return 0;
            }

            @Override
            public Map<Integer, ArrayList<String>> currentCommands() {
                return null;
            }
        });
        game.setChangeSRListener(new IChangeSpeechRecognizerCallback() {
            @Override
            public void stoppedSpeechRecognizer() {

            }

            @Override
            public void startSpeechRecognizer() {

            }
        });
        return new Lwjgl3Application(game, getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("ProtectTheSurvivors");
        //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
        //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
        //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        configuration.setWindowedMode(1400, 920);
        //// You can change these files; they are in lwjgl3/src/main/resources/ .
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
