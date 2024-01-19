package bytelib.auth;

import bytelib.Library;
import bytelib.dto.LoginRequest;
import bytelib.dto.LoginResponse;
import bytelib.dto.RegistrationRequest;
import bytelib.dto.RegistrationResponse;
import bytelib.persistence.DBConnector;
import bytelib.users.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthServer2 {
    private static final int PORT = 8888;
    private static final int INITIAL_THREAD_POOL_SIZE = 5;
    private static final int MAX_THREAD_POOL_SIZE = 15;
    private final Library library;
    private final ThreadPoolManager threadPoolManager;
    private static final Logger LOGGER = Logger.getLogger(AuthServer2.class.getName());

    public AuthServer2(Library library) {
        this.library = library;
        this.threadPoolManager = new ThreadPoolManager(INITIAL_THREAD_POOL_SIZE, MAX_THREAD_POOL_SIZE);
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("New client connected: " + clientSocket);

                threadPoolManager.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error starting server", e);
        }
    }

    private void handleClient(Socket clientSocket) {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            String requestType = (String) in.readObject();
            switch (requestType) {
                case "LOGIN":
                    handleLoginRequest(in, out);
                    break;
                case "REGISTER":
                    handleRegistrationRequest(in, out);
                    break;
                default:
                    LOGGER.warning("Unknown request type: " + requestType);
            }

        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error handling client", e);
        } finally {
            closeSocket(clientSocket);
            threadPoolManager.releaseThread();
        }
    }

    public void handleLoginRequest(ObjectInputStream in, ObjectOutputStream out) {
        try {
            LoginRequest loginRequest = (LoginRequest) in.readObject();
            LoginResponse loginResponse = processLoginRequest(loginRequest);
            out.writeObject(loginResponse);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error processing login request", e);
            sendErrorResponse(out);
        }
    }

    public void handleRegistrationRequest(ObjectInputStream in, ObjectOutputStream out) {
        try {
            RegistrationRequest registrationRequest = (RegistrationRequest) in.readObject();
            RegistrationResponse registrationResponse = processRegistrationRequest(registrationRequest);
            out.writeObject(registrationResponse);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error processing registration request", e);
            sendErrorResponse(out);
        }
    }

    private LoginResponse processLoginRequest(LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.usernameOrEmail();
        String password = loginRequest.password();
        User authenticatedUser = library.loginBorrower(usernameOrEmail, password);
        return new LoginResponse(authenticatedUser != null, authenticatedUser);
    }

    private RegistrationResponse processRegistrationRequest(RegistrationRequest registrationRequest) {
        String username = registrationRequest.username();
        String email = registrationRequest.email();
        String phone = registrationRequest.phone();
        String password = registrationRequest.password();
        String accountType = registrationRequest.accountType();
        boolean registerSuccess = library.registerUser(username, password, email, phone, accountType);
        return new RegistrationResponse(registerSuccess, registerSuccess ? "Registration Successful" : "Registration failed");
    }

    private void closeSocket(Socket clientSocket) {
        try {
            clientSocket.close();
            LOGGER.info("Client socket closed");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error closing client socket", e);
        }
    }

    private void sendErrorResponse(ObjectOutputStream out) {
        try {
            out.writeObject(new LoginResponse(false, null));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error sending error response", e);
        }
    }

    public static void main(String[] args) {
        Connection dbConnection = DBConnector.getConnection();
        Library library = loadLibrary(dbConnection);
        AuthServer2 authServer = new AuthServer2(library);
        authServer.startServer();
    }

    private static Library loadLibrary(Connection dbConnection) {
        Library library = null;

        try {
            library = new Library(dbConnection);
            LOGGER.info("Library loaded successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading library", e);
        }

        return library;
    }

    private static class ThreadPoolManager {
        private final ThreadPoolExecutor threadPool;

        public ThreadPoolManager(int initialSize, int maxSize) {
            this.threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(initialSize);
            this.threadPool.setMaximumPoolSize(maxSize);
        }

        public void execute(Runnable task) {
            threadPool.execute(task);
        }

        public void releaseThread() {
            int activeConnections = threadPool.getActiveCount();
            int currentPoolSize = threadPool.getPoolSize();

            if (currentPoolSize > INITIAL_THREAD_POOL_SIZE && activeConnections < currentPoolSize / 2) {
                int newSize = Math.max(INITIAL_THREAD_POOL_SIZE, currentPoolSize / 2);
                threadPool.setCorePoolSize(newSize);
            }
        }
    }
}
