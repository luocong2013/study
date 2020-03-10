package org.apache.ftpserver.usermanager.impl;

import org.apache.ftpserver.FtpServerConfigurationException;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.usermanager.AnonymousAuthentication;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.util.BaseProperties;
import org.apache.ftpserver.util.IoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption apache ftp properties manager(修改一个文件路径问题，拷贝自源码包)
 * @date 2020/3/10 20:49
 */
public class PropertiesUserManager extends AbstractUserManager {
    private final Logger LOG = LoggerFactory.getLogger(PropertiesUserManager.class);
    private static final String PREFIX = "ftpserver.user.";
    private BaseProperties userDataProp;
    private File userDataFile;
    private URL userUrl;

    /**
     * Instantiates a new Properties user manager.
     *
     * @param passwordEncryptor the password encryptor
     * @param userDataFile      the user data file
     * @param adminName         the admin name
     */
    public PropertiesUserManager(PasswordEncryptor passwordEncryptor, File userDataFile, String adminName) {
        super(adminName, passwordEncryptor);
        this.loadFromFile(userDataFile);
    }

    /**
     * Instantiates a new Properties user manager.
     *
     * @param passwordEncryptor the password encryptor
     * @param userDataPath      the user data path
     * @param adminName         the admin name
     */
    public PropertiesUserManager(PasswordEncryptor passwordEncryptor, URL userDataPath, String adminName) {
        super(adminName, passwordEncryptor);
        this.loadFromUrl(userDataPath);
    }

    private void loadFromFile(File userDataFile) {
        try {
            this.userDataProp = new BaseProperties();
            if (userDataFile != null) {
                this.LOG.debug("File configured, will try loading");
                if (userDataFile.exists()) {
                    this.userDataFile = userDataFile;
                    this.LOG.debug("File found on file system");
                    FileInputStream fis = null;

                    try {
                        fis = new FileInputStream(userDataFile);
                        this.userDataProp.load(fis);
                    } finally {
                        IoUtils.close(fis);
                    }
                } else {
                    this.LOG.debug("File not found on file system, try loading from classpath");
                    InputStream is = this.getClass().getClassLoader().getResourceAsStream(replaceBackSlash(userDataFile.getPath()));
                    if (is == null) {
                        throw new FtpServerConfigurationException("User data file specified but could not be located, neither on the file system or in the classpath: " + userDataFile.getPath());
                    }
                    try {
                        this.userDataProp.load(is);
                    } finally {
                        IoUtils.close(is);
                    }
                }
            }
        } catch (IOException var13) {
            throw new FtpServerConfigurationException("Error loading user data file : " + userDataFile, var13);
        }
    }
    private static String replaceBackSlash(String path){
        return path.replace('\\','/');
    }

    private void loadFromUrl(URL userDataPath) {
        try {
            this.userDataProp = new BaseProperties();
            if (userDataPath != null) {
                this.LOG.debug("URL configured, will try loading");
                this.userUrl = userDataPath;
                InputStream is = null;
                is = userDataPath.openStream();

                try {
                    this.userDataProp.load(is);
                } finally {
                    IoUtils.close(is);
                }
            }

        } catch (IOException var7) {
            throw new FtpServerConfigurationException("Error loading user data resource : " + userDataPath, var7);
        }
    }

    /**
     * Refresh.
     */
    public void refresh() {
        synchronized(this.userDataProp) {
            if (this.userDataFile != null) {
                this.LOG.debug("Refreshing user manager using file: " + this.userDataFile.getAbsolutePath());
                this.loadFromFile(this.userDataFile);
            } else {
                this.LOG.debug("Refreshing user manager using URL: " + this.userUrl.toString());
                this.loadFromUrl(this.userUrl);
            }

        }
    }

    /**
     * Gets file.
     *
     * @return the file
     */
    public File getFile() {
        return this.userDataFile;
    }

    @Override
    public synchronized void save(User usr) throws FtpException {
        if (usr.getName() == null) {
            throw new NullPointerException("User name is null.");
        } else {
            String thisPrefix = "ftpserver.user." + usr.getName() + '.';
            this.userDataProp.setProperty(thisPrefix + "userpassword", this.getPassword(usr));
            String home = usr.getHomeDirectory();
            if (home == null) {
                home = "/";
            }

            this.userDataProp.setProperty(thisPrefix + "homedirectory", home);
            this.userDataProp.setProperty(thisPrefix + "enableflag", usr.getEnabled());
            this.userDataProp.setProperty(thisPrefix + "writepermission", usr.authorize(new WriteRequest()) != null);
            this.userDataProp.setProperty(thisPrefix + "idletime", usr.getMaxIdleTime());
            TransferRateRequest transferRateRequest = new TransferRateRequest();
            transferRateRequest = (TransferRateRequest)usr.authorize(transferRateRequest);
            if (transferRateRequest != null) {
                this.userDataProp.setProperty(thisPrefix + "uploadrate", transferRateRequest.getMaxUploadRate());
                this.userDataProp.setProperty(thisPrefix + "downloadrate", transferRateRequest.getMaxDownloadRate());
            } else {
                this.userDataProp.remove(thisPrefix + "uploadrate");
                this.userDataProp.remove(thisPrefix + "downloadrate");
            }

            ConcurrentLoginRequest concurrentLoginRequest = new ConcurrentLoginRequest(0, 0);
            concurrentLoginRequest = (ConcurrentLoginRequest)usr.authorize(concurrentLoginRequest);
            if (concurrentLoginRequest != null) {
                this.userDataProp.setProperty(thisPrefix + "maxloginnumber", concurrentLoginRequest.getMaxConcurrentLogins());
                this.userDataProp.setProperty(thisPrefix + "maxloginperip", concurrentLoginRequest.getMaxConcurrentLoginsPerIP());
            } else {
                this.userDataProp.remove(thisPrefix + "maxloginnumber");
                this.userDataProp.remove(thisPrefix + "maxloginperip");
            }

            this.saveUserData();
        }
    }

    private void saveUserData() throws FtpException {
        if (this.userDataFile != null) {
            File dir = this.userDataFile.getAbsoluteFile().getParentFile();
            if (dir != null && !dir.exists() && !dir.mkdirs()) {
                String dirName = dir.getAbsolutePath();
                throw new FtpServerConfigurationException("Cannot create directory for user data file : " + dirName);
            } else {
                FileOutputStream fos = null;

                try {
                    fos = new FileOutputStream(this.userDataFile);
                    this.userDataProp.store(fos, "Generated file - don't edit (please)");
                } catch (IOException var7) {
                    this.LOG.error("Failed saving user data", var7);
                    throw new FtpException("Failed saving user data", var7);
                } finally {
                    IoUtils.close(fos);
                }

            }
        }
    }

    @Override
    public void delete(String usrName) throws FtpException {
        String thisPrefix = "ftpserver.user." + usrName + '.';
        Enumeration<?> propNames = this.userDataProp.propertyNames();
        ArrayList remKeys = new ArrayList();

        while(propNames.hasMoreElements()) {
            String thisKey = propNames.nextElement().toString();
            if (thisKey.startsWith(thisPrefix)) {
                remKeys.add(thisKey);
            }
        }

        Iterator remKeysIt = remKeys.iterator();

        while(remKeysIt.hasNext()) {
            this.userDataProp.remove(remKeysIt.next());
        }

        this.saveUserData();
    }

    private String getPassword(User usr) {
        String name = usr.getName();
        String password = usr.getPassword();
        if (password != null) {
            password = this.getPasswordEncryptor().encrypt(password);
        } else {
            String blankPassword = this.getPasswordEncryptor().encrypt("");
            if (this.doesExist(name)) {
                String key = "ftpserver.user." + name + '.' + "userpassword";
                password = this.userDataProp.getProperty(key, blankPassword);
            } else {
                password = blankPassword;
            }
        }

        return password;
    }

    @Override
    public String[] getAllUserNames() {
        String suffix = ".homedirectory";
        ArrayList<String> ulst = new ArrayList();
        Enumeration<?> allKeys = this.userDataProp.propertyNames();
        int prefixlen = "ftpserver.user.".length();
        int suffixlen = suffix.length();

        while(allKeys.hasMoreElements()) {
            String key = (String)allKeys.nextElement();
            if (key.endsWith(suffix)) {
                String name = key.substring(prefixlen);
                int endIndex = name.length() - suffixlen;
                name = name.substring(0, endIndex);
                ulst.add(name);
            }
        }

        Collections.sort(ulst);
        return (String[])ulst.toArray(new String[0]);
    }

    @Override
    public User getUserByName(String userName) {
        if (!this.doesExist(userName)) {
            return null;
        } else {
            String baseKey = "ftpserver.user." + userName + '.';
            BaseUser user = new BaseUser();
            user.setName(userName);
            user.setEnabled(this.userDataProp.getBoolean(baseKey + "enableflag", true));
            user.setHomeDirectory(this.userDataProp.getProperty(baseKey + "homedirectory", "/"));
            List<Authority> authorities = new ArrayList();
            if (this.userDataProp.getBoolean(baseKey + "writepermission", false)) {
                authorities.add(new WritePermission());
            }

            int maxLogin = this.userDataProp.getInteger(baseKey + "maxloginnumber", 0);
            int maxLoginPerIP = this.userDataProp.getInteger(baseKey + "maxloginperip", 0);
            authorities.add(new ConcurrentLoginPermission(maxLogin, maxLoginPerIP));
            int uploadRate = this.userDataProp.getInteger(baseKey + "uploadrate", 0);
            int downloadRate = this.userDataProp.getInteger(baseKey + "downloadrate", 0);
            authorities.add(new TransferRatePermission(downloadRate, uploadRate));
            user.setAuthorities(authorities);
            user.setMaxIdleTime(this.userDataProp.getInteger(baseKey + "idletime", 0));
            return user;
        }
    }

    @Override
    public boolean doesExist(String name) {
        String key = "ftpserver.user." + name + '.' + "homedirectory";
        return this.userDataProp.containsKey(key);
    }

    @Override
    public User authenticate(Authentication authentication) throws AuthenticationFailedException {
        if (authentication instanceof UsernamePasswordAuthentication) {
            UsernamePasswordAuthentication upauth = (UsernamePasswordAuthentication)authentication;
            String user = upauth.getUsername();
            String password = upauth.getPassword();
            if (user == null) {
                throw new AuthenticationFailedException("Authentication failed");
            } else {
                if (password == null) {
                    password = "";
                }

                String storedPassword = this.userDataProp.getProperty("ftpserver.user." + user + '.' + "userpassword");
                if (storedPassword == null) {
                    throw new AuthenticationFailedException("Authentication failed");
                } else if (this.getPasswordEncryptor().matches(password, storedPassword)) {
                    return this.getUserByName(user);
                } else {
                    throw new AuthenticationFailedException("Authentication failed");
                }
            }
        } else if (authentication instanceof AnonymousAuthentication) {
            if (this.doesExist("anonymous")) {
                return this.getUserByName("anonymous");
            } else {
                throw new AuthenticationFailedException("Authentication failed");
            }
        } else {
            throw new IllegalArgumentException("Authentication not supported by this user manager");
        }
    }

    /**
     * Dispose.
     */
    public synchronized void dispose() {
        if (this.userDataProp != null) {
            this.userDataProp.clear();
            this.userDataProp = null;
        }
    }
}
