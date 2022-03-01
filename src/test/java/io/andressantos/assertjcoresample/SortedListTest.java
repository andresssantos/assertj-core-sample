package io.andressantos.assertjcoresample;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SortedListTest {

    @Test
    void shouldAssertThatListIsSorted() {
        Transaction transaction1 = Transaction.builder().id(1L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction2 = Transaction.builder().id(2L).value(BigDecimal.TEN).date(LocalDate.now().minusDays(1)).build();

        List<Transaction> transactionList = List.of(transaction1, transaction2)
                .stream()
                .sorted(Comparator.comparing(Transaction::getDate))
                .collect(Collectors.toList());

        assertThat(transactionList).isSortedAccordingTo(Comparator.comparing(Transaction::getDate));
    }

    @Test
    void shouldAssertThatListIsSortedAndEqualsToAnotherList() {
        Transaction transaction1 = Transaction.builder().id(1L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction2 = Transaction.builder().id(2L).value(BigDecimal.TEN).date(LocalDate.now().minusDays(1)).build();

        List<Transaction> firstTransactionList = List.of(transaction1, transaction2)
                .stream()
                .sorted(Comparator.comparing(Transaction::getDate))
                .collect(Collectors.toList());

        List<Transaction> secondTransactionList = List.of(transaction1, transaction2)
                .stream()
                .sorted(Comparator.comparing(Transaction::getDate))
                .collect(Collectors.toList());

        assertThat(firstTransactionList)
                .isEqualTo(secondTransactionList)
                .isSortedAccordingTo(Comparator.comparing(Transaction::getDate));
    }

}