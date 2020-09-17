package com.moortahc.server.common.utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BSUtility{
    
    // Attributes
    public static int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    public final static Random RANDOM = new Random();
    // public static ExecutorService EXECUTOR = Executors.newUnboundedVirtualThreadExecutor();
    // public static ScheduledExecutorService SCHEDULED_EXECUTOR =
    // Executors.newScheduledThreadPool(Integer.MAX_VALUE, Thread.builder().virtual().factory());
    public static ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);
    public static ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(10);
    public static int START = 0;
    public static int END = 1;
    
    public enum Orientation{
        HORIZONTAL, VERTICAL
    }
    
    // Good Way to check if string contains not characters
    // !someString.chars().allMatch(Character::isLetter)
    
    // Start Methods
    
    // public static <T> T fromString(String json)
    // throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException{
    // JsonNode dataJson = mapper.readValue(json, JsonNode.class);
    // String className = dataJson.get("genericType").textValue();
    // Class<?> clazz = Class.forName(className);
    // JavaType type = mapper.getTypeFactory().constructParametricType(BSNetworkPackage.class, clazz);
    // return mapper.readValue(json, type);
    // }
    //
    // public static <T> String toString(T bSNP) throws JsonProcessingException{
    // return mapper.writeValueAsString(bSNP);
    // }
    
    /**
     * Read the object from Base64 string.
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromString(String s) throws IOException, ClassNotFoundException{
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        T o = (T) ois.readObject();
        ois.close();
        return o;
    }
    
    /** Write the object to a Base64 string. */
    public static String toString(Serializable o) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
    
    /**
     * Write an object to file
     * https://examples.javacodegeeks.com/core-java/io/fileoutputstream/how-to-write-an-object-to-file-in-java/
     * @throws IOException
     */
    
    public static boolean containsAnyTrues(boolean... values){
        for(boolean value : values){
            if(value){
                return true;
            }
        }
        return false;
    }
    
    public static int round(double i, double v){
        return (int) (Math.round(i / v) * v);
    }
    
    public static <T> T[] concatAll(T[] first, T[]... rest){
        int totalLength = first.length;
        for(T[] array : rest){
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for(T[] array : rest){
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
    
    public static boolean isDouble(String s){
        try{
            Double.parseDouble(s);
        } catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
    public static boolean isInteger(String s){
        return isInteger(s, 10);
    }
    
    public static String removeLastCharOptional(String s){
        return Optional.ofNullable(s).filter(str -> str.length() != 0)
                .map(str -> str.substring(0, str.length() - 1)).orElse(s);
    }
    
    public static boolean isInteger(String s, int radix){
        if(s.isEmpty())
            return false;
        for(int i = 0; i < s.length(); i++){
            if(i == 0 && s.charAt(i) == '-'){
                if(s.length() == 1)
                    return false;
                else
                    continue;
            }
            if(Character.digit(s.charAt(i), radix) < 0)
                return false;
        }
        return true;
    }
    
    public static void writeObjectsToFile(String filePath, List<?> writableObjects) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(writableObjects);
        objectOut.close();
    }
    
    public static <T> void writeObjectToFile(String filePath, T writableObject) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(writableObject);
        objectOut.close();
    }
    
    public static <T> T readObjectFromFile(String filePath, Class<T> clazz)
            throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream input = new ObjectInputStream(fis);
        T o = clazz.cast(input.readObject());
        input.close();
        return o;
    }
    
    public static <T> List<T> readObjectsFromFile(String filePath) throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream input = new ObjectInputStream(fis);
        @SuppressWarnings("unchecked")
        List<T> o = (Vector<T>) input.readObject();
        input.close();
        return o;
    }
    
    public static boolean isNullOrEmpty(String s){
        return s == null || s.length() == 0;
    }
    
    public static int mid(int length){
        if(length == 1){
            return 0;
        }
        
        int midIndex = length;
        if(length % 2 == 0){
            midIndex = length / 2;
        } else{
            midIndex = (length / 2) + 1;
        }
        return midIndex;
    }
    
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map){
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
        
        Map<K, V> result = new LinkedHashMap<>();
        for(Entry<K, V> entry : list){
            result.put(entry.getKey(), entry.getValue());
        }
        
        return result;
    }
    
    public static void listFilesForFolder(final File folder){
        for(final File fileEntry : folder.listFiles()){
            if(fileEntry.isDirectory()){
                listFilesForFolder(fileEntry);
            }
        }
    }
    
    public static String generateRandomString(){
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }
    
    public static Byte[] toObjects(byte[] bytesPrim){
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }
    
    public static boolean lockInstance(final String lockFile){
        try{
            final File file = new File(lockFile);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if(fileLock != null){
                Runtime.getRuntime().addShutdownHook(new Thread(){
                    public void run(){
                        try{
                            fileLock.release();
                            randomAccessFile.close();
                            file.delete();
                        } catch(Exception e){
                            log.error("UNABLE TO REMOVE LOCK FILE" + lockFile, e);
                        }
                    }
                });
                return true;
            }
        } catch(Exception e){
            log.error("UNABLE TO CREATE AND/OR LOCK FILE {}", lockFile, e);
        }
        return false;
    }
    
    public static List<String> read(String path) throws IOException{
        InputStream in = BSUtility.class.getResourceAsStream(path);
        List<String> lines = null;
        if(in != null){
            try(BufferedReader input = new BufferedReader(new InputStreamReader(in))){
                lines = new ArrayList<String>();
                String line = null;
                while((line = input.readLine()) != null){
                    lines.add(line);
                }
            }
            
        } else{
            throw new IOException("Could not find" + path);
        }
        
        return lines;
    }
    
    public static Integer[][] determineWorkLoad(int sizeWork){
        int minItemsPerThread = sizeWork / BSUtility.MAX_THREADS;
        int maxItemsPerThread = minItemsPerThread + 1;
        int threadsWithMaxItems = sizeWork - BSUtility.MAX_THREADS * minItemsPerThread;
        Integer[][] work = new Integer[BSUtility.MAX_THREADS][];
        int start = 0;
        for(int i = 0; i < BSUtility.MAX_THREADS; i++){
            work[i] = new Integer[2];
            int itemsCount = (i < threadsWithMaxItems ? maxItemsPerThread : minItemsPerThread);
            int end = start + itemsCount;
            work[i][START] = start;
            work[i][END] = end;
            start = end;
        }
        return work;
    }
    
    public static <T> List<Future<T>> putOnExecutor(Callable<T>[] callables){
        List<Future<T>> futures = new ArrayList<Future<T>>();
        for(Callable<T> callable : callables){
            futures.add(BSUtility.EXECUTOR.submit(callable));
        }
        return futures;
    }
    
    public static boolean deleteDirectory(File directory){
        if(directory.exists()){
            File[] files = directory.listFiles();
            if(null != files){
                for(int i = 0; i < files.length; i++){
                    if(files[i].isDirectory()){
                        deleteDirectory(files[i]);
                    } else{
                        files[i].delete();
                    }
                }
            }
        }
        return (directory.delete());
    }
    
    public static boolean[] bytesToBooleans(byte[] bytes){
        boolean[] bools = new boolean[bytes.length * 8];
        byte[] pos = new byte[] {(byte) 0x80, 0x40, 0x20, 0x10, 0x8, 0x4, 0x2, 0x1};
        
        for(int i = 0; i < bytes.length; i++){
            for(int j = i * 8, k = 0; k < 8; j++, k++){
                bools[j] = (bytes[i] & pos[k]) != 0;
            }
        }
        
        return bools;
    }
    
    // low inclusive : high exclusive
    public static int generateRandomIntInRange(int high, int low){
        return BSUtility.RANDOM.nextInt(high - low) + low;
    }
    
    public static long generateRandomLongInRange(long high, long low){
        return ThreadLocalRandom.current().nextLong(high - low) + low;
    }
    
    public static double generateRandomDoubleInRange(double high, double low){
        return ThreadLocalRandom.current().nextDouble(high - low) + low;
    }
    
    public static String capitalizeFirstLetter(String original){
        if(original == null || original.length() == 0){
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
    
    public static boolean[][] resample(boolean[][] inputBits, int scaleFactor){
        if(inputBits.length <= 0 || inputBits[0].length <= 0 || scaleFactor <= 0){
            throw new IllegalArgumentException("Can't resample an empty boolean array or scale to 0 or less. "
                    + Arrays.deepToString(inputBits) + scaleFactor);
        }
        final int W = (int) inputBits[0].length;
        final int H = (int) inputBits.length;
        final int S = scaleFactor;
        
        boolean[][] outputBits = new boolean[H * S][W * S];
        
        for(int y = 0; y < H; y++){
            for(int x = 0; x < W; x++){
                final boolean value = inputBits[y][x];
                for(int dy = 0; dy < S; dy++){
                    for(int dx = 0; dx < S; dx++){
                        outputBits[y * S + dy][x * S + dx] = value;
                    }
                }
            }
        }
        
        return outputBits;
    }
    
    public static int[][] squareWithValuesLargerTowardsMiddle(int lengthOfSquare){
        int[][] values = new int[lengthOfSquare][lengthOfSquare];
        var layer = 0;
        for(int y = 0; values.length / 2 >= y; y++){
            var yTop = y;
            var yBottom = values.length - yTop - 1;
            for(int x = layer; values[y].length - layer > x; x++){
                values[yTop][x] = layer;
                values[yBottom][x] = layer;
                if(x != layer && x != values[y].length - layer - 1){
                    values[x][yTop] = layer;
                    values[x][yBottom] = layer;
                }
            }
            layer++;
        }
        return values;
    }
    
    public static String millisToElapsedTime(long millis){
        DateFormat fmt = new SimpleDateFormat("mm:ss");
        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        int hours = (int) (millis / 3600000);
        if(hours == 0){
            return fmt.format(new Date(millis));
        }
        return (millis / 3600000/* hours */) + ":" + fmt.format(new Date(millis));
    }
    
    public static String formatToThreeDigits(long amount){
        if(amount == Long.MIN_VALUE){
            return "-9E";
        }
        
        boolean positiveNegative = amount >= 0;
        amount = Math.abs(amount);
        int amountDivided = 0;
        while(amount > 999){
            amount /= 1000;
            amountDivided++;
        }
        String[] endings = {"", "k", "M", "G", "T", "P", "E"};
        return (positiveNegative ? "" : "-") + Long.toString(amount) + endings[amountDivided];
    }
    
    public static String durationToElapsedTime(Duration d){
        return millisToElapsedTime(d.toMillis());
    }
    // End Methods
    
    public static String convertMapToString(Map<?, ?> map){
        return map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
    }
    
    // Not my proudest code
    public static String convertNumberTo(int number, int lengthRequirement){
        if(number < 0){
            return "-";
        }
        int powerOfTen = 0;
        double tempNumber = number;
        while(tempNumber >= 1){
            tempNumber /= 10;
            powerOfTen++;
        }
        if(powerOfTen <= lengthRequirement){
            return Integer.toString(number);
        }
        var modPowerOfTen = powerOfTen % 3;
        if(modPowerOfTen == 0){
            modPowerOfTen = 3;
        }
        var decimalBuilder = new StringBuilder();
        while(modPowerOfTen > 0){
            tempNumber *= 10;
            int tempIntNumber = (int) Math.round(tempNumber);
            decimalBuilder.append(tempIntNumber);
            modPowerOfTen--;
            tempNumber = Math.abs(tempIntNumber - tempNumber);
        }
        String[] endings = {"Z", "K", "M", "B", "T",};
        if(lengthRequirement <= decimalBuilder.length()){
            decimalBuilder.append(endings[powerOfTen / 3]);
            return decimalBuilder.toString();
        }
        decimalBuilder.append(".");
        while(lengthRequirement > decimalBuilder.length() - 1){
            int tempIntNumber = (int) tempNumber;
            tempNumber = Math.abs(tempIntNumber - tempNumber) * 10;
            decimalBuilder.append(((int) tempNumber) % 10);
        }
        decimalBuilder.append(endings[powerOfTen / 3]);
        return decimalBuilder.toString();
    }
    
    public static <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> futures){
        CompletableFuture<Void> allFuturesResult =
                CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        return allFuturesResult.thenApply(v -> {
            return futures.stream().map(future -> future.join()).collect(Collectors.toList());
        });
    }
}
