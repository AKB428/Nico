package akb428.dao;

import java.sql.Connection;

public abstract class AbstractMediaUrlDao implements IMediaUrlDao{

	protected static final String TABLE_NAME = "media_url";
	protected static final String TABLE_HISTORY_NAME = "media_url_histroy";
	protected Connection con = null;
}
