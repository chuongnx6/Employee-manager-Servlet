package payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListData extends ResponseData{
	private long firstRow;
	private int currentPage;
	private int pageSize;
	private long totalResult;
	private int totalPage;
}
