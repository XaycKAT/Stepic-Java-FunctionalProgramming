import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summingLong;

public class Main {
    //2.4
    public static BiFunction<Long, Long, Long> bifun = (x, y) -> {
        Long res = 1L;
        for (Long i = x; i <= y; ++i)
            res *= i;
        return res;
    };

    //2.5
    public static Function<List<String>, List<String>> fun = strings -> {
        Set<String> set = new HashSet<String>();
        for (String s : strings)
            set.add(s);
        List list = new ArrayList();
        list.addAll(set);
        Collections.sort(list);
        return list;
    };

    //2.9
    public static <T> List<T> filter(List<T> elems, Predicate<T> predicate) {
        List<T> res = new ArrayList<T>();
        for (T t : elems) {
            if (predicate.test(t))
                res.add(t);
        }
        return res;
    }

    public static List<Account> nonEmptyAccounts(List<Account> accounts) {
        return filter(accounts, account -> account.getBalance() > 0);
    }

    public static List<Account> accountsWithTooMuchMoney(List<Account> accounts) {
        return filter(accounts, account -> account.getBalance() >= 100_000_000L && !account.isLocked);
    }

    //2.10
    public static final TernaryIntPredicate allValuesAreDifferentPredicate = (x, y, z) -> {
        if (!x.equals(y) && !y.equals(z) && !x.equals(z))
            return true;
        return false;
    };

    //2.12

    /**
     * The method represents a disjunct operator for a list of predicates.
     * For an empty list it returns the always false predicate.
     */
    public static IntPredicate disjunctAll(List<IntPredicate> predicates) {
        IntPredicate res = new IntPredicate() {
            @Override
            public boolean test(int value) {
                return false;
            }
        };
        for (IntPredicate intPredicate : predicates) {
            res = res.or(intPredicate);
        }
        return res;
    }


    private static class Student {
        private String name;
        private Integer csGrade;

        public Student(String name, Integer csGrade) {
            this.name = name;
            this.csGrade = csGrade;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", csGrade=" + csGrade +
                    '}';
        }

        public String getName() {
            return name;
        }

        public Integer getCsGrade() {
            return csGrade;
        }
    }

    //2.17
    public static boolean isPrime(final long number) {
        return LongStream.rangeClosed(2, number / 2).noneMatch(i -> number % i == 0);
    }

    //2.18
    public static Stream<String> createBadWordsDetectingStream(String text, List<String> badWords) {
        return Arrays.stream(text.split(" ")).filter(x -> badWords.contains(x)).distinct().sorted();
    }

    //2.19
    public static IntStream createFilteringStream(IntStream evenStream, IntStream oddStream) {
        return IntStream.concat(evenStream, oddStream).filter(x -> x % 15 == 0).sorted().skip(2);
    }

    public static IntPredicate negateEachAndConjunctAll(Collection<IntPredicate> predicates) {
        return predicates.stream()
                .map(IntPredicate::negate)
                .reduce(n -> true, IntPredicate::and);
    }

    //2.20

    /**
     * Calculates the factorial of the given number n
     *
     * @param n >= 0
     * @return factorial value
     */
    public static long factorial(long n) {
        return LongStream.rangeClosed(1, n).reduce(1, (acc, ele) -> acc * ele);
    }

    //2.21

    /**
     * The method calculates the sum of odd numbers in the given range
     *
     * @param start of a range, start >= 0
     * @param end   of a range (inclusive), end >= start
     * @return sum of odd numbers
     */
    public static long sumOfOddNumbersInRange(long start, long end) {
        return LongStream.rangeClosed(start, end).filter(x -> x % 2 != 0).sum();
    }

    //2.23

    /**
     * Calculates the number of employees with salary >= threshold (only for 111- departments)
     *
     * @param departments are list of departments
     * @param threshold   is lower edge of salary
     * @return the number of employees
     */
    public static long calcNumberOfEmployees(List<Department> departments, long threshold) {
        return departments.stream().filter(x -> x.getCode().startsWith("111-"))
                .flatMap(x -> x.employees.stream().filter(y -> y.getSalary() >= threshold))
                .count();
    }

    //2.24

    /**
     * Calculates the general sum of canceled transactions for all non empty accounts in the list
     */
    public static long calcSumOfCanceledTransOnNonEmptyAccounts(List<Account> accounts) {
        // write your code here
        return accounts
                .stream()
                .filter(a -> a.getBalance() > 0)
                .flatMap(a -> a.getTransactions().stream())
                .filter(t -> t.getState() == Transaction.State.CANCELED)
                .mapToLong(Transaction::getSum)
                .sum();
    }

    //2.26
    public static long productOfSqr(List<Integer> numbers) {
        return numbers.stream().collect(Collectors.reducing(1, x -> x * x, (x, y) -> x * y));
    }

    //2.27
    public static Map<Boolean, List<String>> palindromeOrNoMap(String[] words) {
        return Arrays.stream(words)
                .collect(Collectors.partitioningBy(x -> x.equals(new StringBuilder(x).reverse().toString())));
    }

    //2.28
    public static Map<String, Long> totalSumOfTransByEachAccount(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getAccount().getNumber(), summingLong(Transaction::getSum)));
    }

    //2.29
    public static Map<String, Long> clickCount(List<LogEntry> logs) {
        return logs.stream()
                .collect(Collectors.groupingBy(t -> t.getUrl(), counting()));
    }

    //2.31
//    public static LongStream createPrimesFilteringStream(long rangeBegin, long rangeEnd) {
//        return LongStream.rangeClosed(rangeBegin, rangeEnd).filter(x -> NumberUtils.isPrime(x)).parallel();
//    }
//
    //2.34
    /**
     * The function accepts a list of mappers and returns an operator that accepts a list of integers
     * and sequentially applies each mapper to each value (perform a transformation)
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            operators -> args -> {
                IntUnaryOperator mapper = operators.stream()
                        .reduce(i -> i, IntUnaryOperator::andThen);
                return args.stream()
                        .map(mapper::applyAsInt)
                        .collect(Collectors.toList());
            };
    /**
     * EXAMPLE: the operator transforms each number to the same number (perform the identity transformation)
     * <p>
     * It returns a list of the same numbers.
     */
    public static final UnaryOperator<List<Integer>> identityTransformation =
            multifunctionalMapper.apply(Arrays.asList(x -> x, x -> x, x -> x));

    /**
     * The operator accepts an integer list.
     * It multiplies by two each integer number and then add one to its.
     * <p>
     * The operator returns transformed integer list.
     */
    public static final UnaryOperator<List<Integer>> multTwoAndThenAddOneTransformation =
            multifunctionalMapper.apply(Arrays.asList(x -> 2 * x, x -> x + 1));

    /**
     * The operator accepts an integer list.
     * It squares each integer number and then get the next even number following it.
     * <p>
     * The operator returns transformed integer list.
     */
    public static final UnaryOperator<List<Integer>> squareAndThenGetNextEvenNumberTransformation =
            multifunctionalMapper.apply(Arrays.asList(
                    x -> x * x,
                    x -> x % 2 == 0 ? x + 2 : x + 1
            ));

    //2.35
    public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator =
            (seed, combiner) ->
                    (l, r) -> {
                        int acc = seed;
                        for (int el = l; el <= r; el++) {
                            acc = combiner.applyAsInt(acc, el);
                        }
                        return acc;
                    };

    /**
     * An operator that calculates the sum in the given range (inclusively)
     */
    public static final IntBinaryOperator sumOperator = reduceIntOperator.apply(0, (x, y) -> x + y);

    /**
     * An operator that calculates the product in the given range (inclusively)
     */
    public static final IntBinaryOperator productOperator = reduceIntOperator.apply(1, (x, y) -> x * y);


    //2.38
    private static final Set<User> users = new HashSet<>();

    public static Optional<User> findUserByLogin(String login) {
        return users.stream().filter(u -> u.getLogin().equals(login)).findAny();
    }

    public static void printBalanceIfNotEmpty(String userLogin) {
        findUserByLogin(userLogin)
                .map(User::getAccount)
                .map(Account::getBalance)
                .filter(v -> v > 0)
                .map(v -> new String(userLogin + ": " + v))
                .ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        List<Integer> numbers = Stream.of(1, 2, 3).collect(Collectors.toList());
        System.out.println(identityTransformation.apply(numbers));
        System.out.println(multTwoAndThenAddOneTransformation.apply(numbers));
        System.out.println(squareAndThenGetNextEvenNumberTransformation.apply(numbers));


    }
}
